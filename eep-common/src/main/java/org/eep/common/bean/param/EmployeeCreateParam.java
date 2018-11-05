package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateParam extends Param {

	private static final long serialVersionUID = 6962206545790377061L;

	@Min(1)
	private long uid;
	@NotEmpty
	private String cid;
}
