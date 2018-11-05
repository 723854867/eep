package org.eep.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.eep.common.Codes;
import org.eep.common.Consts;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.model.RegionIdGenerator;
import org.eep.common.bean.model.RegionNode;
import org.eep.common.bean.param.RegionCreateParam;
import org.eep.common.bean.param.RegionModifyParam;
import org.eep.manager.RegionManager;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.exception.AssertException;
import org.rubik.bean.core.model.MultiListMap;
import org.rubik.bean.core.model.Pair;
import org.rubik.bean.core.param.LidParam;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.CollectionUtil;
import org.springframework.stereotype.Service;

@Service("rubikRegionService")
public class RegionService {
	
	@Resource
	private RegionManager regionManager;
	@Resource
	private RubikConfigService rubikConfigService;
	
	public SysRegion region(long id) {
		return regionManager.region(id);
	}

	public void delete(LidParam param) {
		regionManager.delete(param);
	}

	public void modify(RegionModifyParam param) {
		regionManager.modify(param);
	}

	public SysRegion create(RegionCreateParam param) {
		return regionManager.create(param);
	}
	
	public void grant(long granter, long grantee, long region) {
		regionManager.grant(granter, grantee, region);
	}
	
	public void reclaim(long reclaimer, long reclaimee, long region) {
		regionManager.reclaim(reclaimer, reclaimee, region);
	}

	public List<RegionNode> regions(Long uid, boolean chain) {
		List<SysRegion> regions = regionManager.regions();
		List<SysRegion> owns = null == uid ? null : regionManager.regions(uid);
		return _nodes(regions, owns, chain);
	}
	
	private List<RegionNode> _nodes(List<SysRegion> regions, List<SysRegion> owns, boolean chain) {
		List<RegionNode> list = new ArrayList<RegionNode>();
		if (CollectionUtil.isEmpty(regions))
			return list;
		List<Pair<Long, Long>> ranges = new ArrayList<Pair<Long, Long>>();
		if (!CollectionUtil.isEmpty(owns)) 
			owns.forEach(area -> ranges.add(new RegionIdGenerator(area.getId(), area.getLayer()).range()));
		Collections.sort(regions, (o1, o2) -> o1.getLayer() - o2.getLayer());
		int minLayer = regions.get(0).getLayer();
		MultiListMap<Integer, SysRegion> map = new MultiListMap<Integer, SysRegion>();
		regions.forEach(area -> map.add(area.getLayer(), area));
		MultiListMap<String, RegionNode> tree = new MultiListMap<String, RegionNode>();
		int layer = minLayer;
		while (true) {
			List<SysRegion> l = map.remove(layer);
			if (null == l)
				break;
			MultiListMap<String, RegionNode> temp = new MultiListMap<String, RegionNode>();
			for (SysRegion region : l) {
				RegionNode node = new RegionNode(region);
				for (Pair<Long, Long> range : ranges) {
					if (region.getId() >= range.getKey() && region.getId() <= range.getValue())
						node.setOwn(true);
				}
				temp.add(region.getTrunk(), node);
				List<RegionNode> parents = tree.get(region.getTrunk());
				if (!CollectionUtil.isEmpty(parents)) {
					for (RegionNode parent : parents) {
						if (region.getLeft() > parent.getLeft() && region.getRight() < parent.getRight()) {
							if (!chain)
								parent.addChild(node);
							else
								node.setParent(parent.getId());
						}
					}
				}
				if (chain) 
					list.add(node);
				else if (layer == minLayer)
					list.add(node);
			}
			tree = temp;
			layer++;
		}
		return list;
	}
	
	public SysRegion userRegionVerify(long uid, long region) {
		SysRegion sysRegion = Assert.notNull(regionManager.region(region), Codes.REGION_NOT_EXIST);
		long root = rubikConfigService.config(Consts.ROOT_UID);
		if (uid != root) {
			List<SysRegion> areas = regionManager.regions(uid);
			for (SysRegion temp : areas) {
				RegionIdGenerator geo = new RegionIdGenerator(temp.getId(), temp.getLayer());
				Pair<Long, Long> range = geo.range();
				if (range.getKey() <= region && range.getValue() >= region)
					return sysRegion;
			}
			throw AssertException.error(Codes.REGION_UNPERMISSION);
		}
		return sysRegion;
	}
	
	public void userRegionInherit(long inheriter, long inheritee) {
		regionManager.userRegionInherit(inheriter, inheritee);
	}
}
