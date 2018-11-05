package org.eep.common.bean.param;

import javax.validation.constraints.Min;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionModifyParam extends Param {

	private static final long serialVersionUID = -1066718119744437566L;

	@Min(1)
	private long region;
	private String name;
	private Boolean open;
}
