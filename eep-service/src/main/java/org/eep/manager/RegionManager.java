package org.eep.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.eep.common.Codes;
import org.eep.common.Consts;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.RegionIdGenerator;
import org.eep.common.bean.param.RegionCreateParam;
import org.eep.common.bean.param.RegionModifyParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.SysRegionDao;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.exception.AssertException;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Pair;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.LidParam;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.CollectionUtil;
import org.rubik.util.common.DateUtil;
import org.rubik.util.common.StringUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RegionManager {
	
	@Resource
	private UserManager userManager;
	@Resource
	private SysRegionDao sysRegionDao;
	@Resource
	private RubikConfigService rubikConfigService;

	@Transactional
	public SysRegion create(RegionCreateParam param) {
		long id = 0;
		int left = 1;
		SysRegion parent = null;
		if (null != param.getParent()) {
			Map<Long, SysRegion> regions = sysRegionDao.lockTrunkById(param.getParent());
			parent = Assert.notNull(regions.get(param.getParent()), Codes.REGION_PARENT_NOT_EXIST);
			Assert.isTrue(parent.getLayer() + 1 <= RegionIdGenerator.MAX_LEVEL, Codes.REGION_LAYER_LIMIT);
			List<SysRegion> list = new ArrayList<SysRegion>();
			SysRegion temp = parent;
			regions.values().forEach(region -> {
				if (region.getLeft() >= temp.getLeft() && region.getRight() <= temp.getRight() && region.getLayer() == temp.getLayer() + 1)
					list.add(region);
			});
			list.sort((o1, o2) ->  {
				if ((o1.getId() - o2.getId()) > 0)
					return -1;
				else if ((o1.getId() - o2.getId()) < 0)
					return 1;
				return 0;
			});
			if (CollectionUtil.isEmpty(list))
				id = new RegionIdGenerator(parent.getId(), parent.getLayer()).childFirstId();
			else {
				SysRegion tem = list.get(0);
				id = new RegionIdGenerator(tem.getId(), tem.getLayer()).nextId();
			}
			left = parent.getRight();
			Iterator<SysRegion> iterator = regions.values().iterator();
			int pright = parent.getRight();
			while (iterator.hasNext()) {
				SysRegion region = iterator.next();
				boolean modified = false;
				if (region.getLeft() > pright) {
					modified = true;
					region.setLeft(region.getLeft() + 2);
					region.setUpdated(DateUtil.current());
				}
				if (region.getRight() >= pright) {
					modified = true;
					region.setRight(region.getRight() + 2);
					region.setUpdated(DateUtil.current());
				}
				if (!modified)
					iterator.remove();
			}
			if (!CollectionUtil.isEmpty(regions))
				sysRegionDao.replaceMap(regions);
		} else {
			Long maxGeo = sysRegionDao.max();
			id = null == maxGeo ? RegionIdGenerator.initialId() : new RegionIdGenerator(maxGeo, 1).nextId();
		}
		SysRegion region = EntityGenerator.newSysRegion(parent, left, param, id);
		try {
			sysRegionDao.insert(region);
		} catch (DuplicateKeyException e) {
			throw AssertException.error(Codes.REGION_EXIST);
		}
		return region;
	}
	
	@Transactional
	public void modify(RegionModifyParam param) { 
		Map<Long, SysRegion> regions = sysRegionDao.lockTrunkById(param.getRegion());
		SysRegion region = Assert.notNull(regions.get(param.getRegion()), Codes.REGION_NOT_EXIST);
		if (StringUtil.hasText(param.getName())) {
			region.setName(param.getName());
			region.setUpdated(DateUtil.current());
		}
		if (null != param.getOpen() && region.isOpen() ^ param.getOpen()) {
			Iterator<SysRegion> iterator = regions.values().iterator();
			while (iterator.hasNext()) {
				SysRegion temp = iterator.next();
				if ((param.getOpen() && temp.getLeft() >= region.getLeft() && temp.getRight() <= region.getRight())
						|| (!param.getOpen() && temp.getLeft() <= region.getLeft() && temp.getRight() >= region.getRight())) {
					temp.setOpen(param.getOpen());
					temp.setUpdated(DateUtil.current());
				} else
					iterator.remove();
			}
			if (!CollectionUtil.isEmpty(regions))
				sysRegionDao.replaceMap(regions);
		} else
			sysRegionDao.update(region);
	}
	
	@Transactional
	public void delete(LidParam param) {
		Map<Long, SysRegion> regions = sysRegionDao.lockTrunkById(param.getId());
		SysRegion region = Assert.notNull(regions.get(param.getId()), Codes.REGION_NOT_EXIST);
		int delt = region.getRight() - region.getLeft() + 1;
		Iterator<SysRegion> iterator = regions.values().iterator();
		Set<Long> deleted = new HashSet<Long>();
		int right = region.getRight();
		while (iterator.hasNext()) {
			SysRegion temp = iterator.next();
			if (temp.getLeft() >= region.getLeft() && temp.getRight() <= region.getRight()) {			// 当前模块或者子模块
				deleted.add(temp.getId());
				iterator.remove();
			} else {
				boolean modified = false;
				if (temp.getLeft() > right) {
					modified = true;
					temp.setUpdated(DateUtil.current());
					temp.setLeft(temp.getLeft() - delt);
				}
				if (temp.getRight() > right) {
					modified = true;
					temp.setUpdated(DateUtil.current());
					temp.setRight(temp.getRight() - delt);
				}
				if (!modified)
					iterator.remove();
			}
		}
		Assert.isTrue(deleted.size() == (delt / 2));
		sysRegionDao.deleteByKeys(deleted);
		RegionIdGenerator id = new RegionIdGenerator(region.getId(), region.getLayer());
		Pair<Long, Long> range = id.range();
		userManager.deleteRegion(range.getKey(), range.getValue());
		if (!CollectionUtil.isEmpty(regions))
			sysRegionDao.replaceMap(regions);
	}
	
	@Transactional
	public void grant(User granter, User grantee, long region) {
		Query query = new Query().and(Criteria.eq("id", region)).forUpdate();
		Assert.notNull(sysRegionDao.queryUnique(query), Codes.REGION_NOT_EXIST);
		long root = rubikConfigService.config(Consts.ROOT_UID);
		if (granter.getId() != root) {				// 非 root用户需要判断区域权限
			SysRegion granterRegion = 0 == granter.getRegion() ? null : sysRegionDao.selectByKey(granter.getRegion());
			Assert.notNull(granterRegion, Codes.REGION_UNPERMISSION);
			RegionIdGenerator id = new RegionIdGenerator(granterRegion.getId(), granterRegion.getLayer());
			Pair<Long, Long> range = id.range();
			Assert.isTrue(range.getKey() <= region && range.getValue() >= region, Codes.REGION_UNPERMISSION);
		}
		grantee.setRegion(region);
		grantee.setUpdated(DateUtil.current());
		userManager.update(grantee);
	}
	
	/**
	 * 收回行政区划只能收回子级行政区划
	 */
	@Transactional
	public void reclaim(User reclaimer, User reclaimee) { 
		if (reclaimee.getRegion() == 0)
			return;
		long root = rubikConfigService.config(Consts.ROOT_UID);
		if (reclaimer.getId() != root) {
			SysRegion reclaimerRegion = sysRegionDao.selectByKey(reclaimer.getRegion());
			RegionIdGenerator id = new RegionIdGenerator(reclaimerRegion.getId(), reclaimerRegion.getLayer());
			Pair<Long, Long> range = id.range();
			Assert.isTrue(range.getKey() <= reclaimee.getRegion() && range.getValue() >= reclaimee.getRegion(), Codes.REGION_UNPERMISSION);
		}
		reclaimee.setRegion(0l);
		reclaimee.setUpdated(DateUtil.current());
		userManager.update(reclaimee);
	}
	
	public List<SysRegion> regions() {
		return sysRegionDao.selectList();
	}
	
	public SysRegion region(long id) {
		return sysRegionDao.selectByKey(id);
	}
}
