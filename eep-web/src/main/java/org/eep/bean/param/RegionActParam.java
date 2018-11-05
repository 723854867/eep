package org.eep.bean.param;

import javax.validation.constraints.Min;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionActParam extends Param {

	private static final long serialVersionUID = -2055532131281796912L;

	@Min(1)
	private long uid;
	@Min(1)
	private long region;
}
