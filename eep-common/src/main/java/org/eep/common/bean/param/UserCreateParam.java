package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.eep.common.Consts;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateParam extends Param {

	private static final long serialVersionUID = 3273014628371274644L;

	@NotEmpty
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String uname;
	@NotEmpty
	@Size(min = 2, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String nickname;
	@NotEmpty
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String password;
}
