package org.eep.util;

import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.model.RegionIdGenerator;
import org.eep.common.bean.param.CompaniesParam;
import org.eep.common.bean.param.UsersParam;
import org.rubik.bean.core.model.Pair;

public class RegionUtil {

	public static final void setRange(UsersParam param, SysRegion region) {
		RegionIdGenerator generator = new RegionIdGenerator(region.getId(), region.getLayer());
		Pair<Long, Long> range = generator.range();
		param.setMin(range.getKey());
		param.setMax(range.getValue());
	}
	
	public static final void setRange(CompaniesParam param, SysRegion region) {
		RegionIdGenerator generator = new RegionIdGenerator(region.getId(), region.getLayer());
		Pair<Long, Long> range = generator.range();
		param.setMin(range.getKey());
		param.setMax(range.getValue());
	}
}
