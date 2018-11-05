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
import org.eep.common.bean.entity.UserRegion;
import org.eep.common.bean.model.RegionIdGenerator;
import org.eep.common.bean.param.RegionCreateParam;
import org.eep.common.bean.param.RegionModifyParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.SysRegionDao;
import org.eep.mybatis.dao.UserRegionDao;
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
	private SysRegionDao sysRegionDao;
	@Resource
	private UserRegionDao userRegionDao;
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
		userRegionDao.deleteByQuery(new Query().and(Criteria.in("region", deleted)));
		if (!CollectionUtil.isEmpty(regions))
			sysRegionDao.replaceMap(regions);
	}
	
	@Transactional
	public void userRegionInherit(long inheriter, long inheritee) { 
		userRegionDao.deleteByQuery(new Query().and(Criteria.eq("uid", inheritee)));
		Set<UserRegion> set = new HashSet<UserRegion>();
		regions(inheriter).forEach(region -> set.add(EntityGenerator.newUserRegion(inheritee, region)));
		if (!CollectionUtil.isEmpty(set))
			userRegionDao.insertMany(set);
	}
	
	@Transactional
	public void grant(long granter, long grantee, long region) {
		Query query = new Query().and(Criteria.eq("id", region)).forUpdate();
		SysRegion sysRegion = Assert.notNull(sysRegionDao.queryUnique(query), Codes.REGION_NOT_EXIST);
		long root = rubikConfigService.config(Consts.ROOT_UID);
		Set<Long> granterRegions = null;
		Set<Long> set = new HashSet<Long>();
		if (granter != root) {
			granterRegions = userRegionDao.lockUserRegions(granter);
			Assert.isTrue(!CollectionUtil.isEmpty(granterRegions), Codes.REGION_UNPERMISSION);
			set.addAll(granterRegions);
		}
		Set<Long> acceptorAreas = userRegionDao.lockUserRegions(grantee);
		set.addAll(acceptorAreas);
		Map<Long, SysRegion> areas = CollectionUtil.isEmpty(set) ? null : sysRegionDao.queryMap(new Query().and(Criteria.in("id", set)).forUpdate());
		if (granter != root) {				// 非 root用户需要判断区域权限
			boolean hit = false;
			for (long temp : granterRegions) {
				SysRegion tempRegion = areas.get(temp);
				RegionIdGenerator id = new RegionIdGenerator(tempRegion.getId(), tempRegion.getLayer());
				Pair<Long, Long> range = id.range();
				if (range.getKey() <= region && range.getValue() >= region) {
					hit = true;
					break;
				}
			}
			Assert.isTrue(hit, Codes.REGION_UNPERMISSION);
		}
		if (!CollectionUtil.isEmpty(acceptorAreas)) {
			for (long temp : acceptorAreas) {
				SysRegion tempRegion = areas.get(temp);
				RegionIdGenerator id = new RegionIdGenerator(tempRegion.getId(), tempRegion.getLayer());
				Pair<Long, Long> range = id.range();
				Assert.isTrue(range.getKey() > region || range.getValue() < region, Codes.USER_REGION_EXIST);
			}
		}
		// 先删除当前地区的所有子行政区划的授权，再赋予该地区，避免重叠
		Pair<Long, Long> range = new RegionIdGenerator(sysRegion.getId(), sysRegion.getLayer()).range();
		userRegionDao.deleteByQuery(new Query().and(Criteria.eq("uid", grantee), Criteria.lte("region", range.getValue()), Criteria.gte("region", range.getKey())));
		userRegionDao.insert(EntityGenerator.newUserRegion(grantee, sysRegion));
	}
	
	/**
	 * 收回行政区划只能收回子级行政区划
	 */
	@Transactional
	public void reclaim(long reclaimer, long reclaimee, long region) { 
		long root = rubikConfigService.config(Consts.ROOT_UID);
		if (reclaimer != root) {
			Set<Long> set = userRegionDao.lockUserRegions(reclaimer);
			Assert.isTrue(!CollectionUtil.isEmpty(set), Codes.REGION_UNPERMISSION);
			Query query = new Query().and(Criteria.in("id", set)).forUpdate();
			Map<Long, SysRegion> regions = sysRegionDao.queryMap(query);
			boolean hit = false;
			for (SysRegion temp : regions.values()) {
				RegionIdGenerator id = new RegionIdGenerator(temp.getId(), temp.getLayer());
				Pair<Long, Long> range = id.range();
				if (range.getKey() <= region && range.getValue() >= region) {
					hit = true;
					break;
				}
			}
			Assert.isTrue(hit, Codes.REGION_UNPERMISSION);
			set = userRegionDao.lockUserRegions(reclaimee);
		}
		userRegionDao.deleteByQuery(new Query().and(Criteria.eq("uid", reclaimee), Criteria.eq("region", region)));
	}
	
	public List<SysRegion> regions() {
		return sysRegionDao.selectList();
	}
	
	public SysRegion region(long id) {
		return sysRegionDao.selectByKey(id);
	}
	
	public List<SysRegion> regions(long uid) {
		return userRegionDao.regions(uid);
	}
}
