package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.eep.common.Consts;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdModifyParam extends Param {

	private static final long serialVersionUID = 2030859859494917797L;

	@NotEmpty
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String opassword;
	@NotEmpty
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String npassword;
}
