package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.eep.common.Consts;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryParam extends Param {

	private static final long serialVersionUID = 1050875554237481371L;

	@NotEmpty
	@Size(min = 2, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String code;
	@NotEmpty
	@Size(min = 2, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String name;
}
