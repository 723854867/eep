package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionCreateParam extends Param {

	private static final long serialVersionUID = 4664990945256075537L;

	@NotEmpty
	private String code;
	@Min(1)
	private Long parent;
	@NotEmpty
	private String name;
}
