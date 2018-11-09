package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertStatisticParam extends Param {

	private static final long serialVersionUID = -1180989510672887170L;

	@Null
	private Long min;
	@Null
	private Long max;
	private String cid;
	@Min(1)
	private Long region;
}
