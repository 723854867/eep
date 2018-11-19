package org.eep.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.eep.common.Codes;
import org.eep.common.Consts;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.RegionIdGenerator;
import org.eep.common.bean.model.RegionNode;
import org.eep.common.bean.model.Visitor;
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
	
	public void grant(User granter, User grantee, long region) {
		regionManager.grant(granter, grantee, region);
	}
	
	public void reclaim(User reclaimer, User reclaimee) {
		regionManager.reclaim(reclaimer, reclaimee);
	}

	public List<RegionNode> regions(Visitor visitor, boolean chain) {
		List<SysRegion> regions = regionManager.regions();
		return _nodes(regions, visitor.getRegion(), chain);
	}
	
	private List<RegionNode> _nodes(List<SysRegion> regions, SysRegion own, boolean chain) {
		List<RegionNode> list = new ArrayList<RegionNode>();
		if (CollectionUtil.isEmpty(regions))
			return list;
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
				if (null != own) {
					Pair<Long, Long> range = new RegionIdGenerator(own.getId(), own.getLayer()).range();
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
	
	public SysRegion userRegionVerify(Visitor visitor, long region) {
		SysRegion sysRegion = Assert.notNull(regionManager.region(region), Codes.REGION_NOT_EXIST);
		long root = rubikConfigService.config(Consts.ROOT_UID);
		if (visitor.id() != root) {
			Assert.notNull(visitor.getRegion(), Codes.REGION_UNPERMISSION);
			RegionIdGenerator geo = new RegionIdGenerator(visitor.getRegion().getId(), visitor.getRegion().getLayer());
			Pair<Long, Long> range = geo.range();
			if (range.getKey() <= region && range.getValue() >= region)
				return sysRegion;
			throw AssertException.error(Codes.REGION_UNPERMISSION);
		}
		return sysRegion;
	}
}
