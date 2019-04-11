package org.eep.common.bean.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eep.common.Consts;
import org.rubik.bean.core.param.Param;
import org.rubik.util.validator.Mobile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyParam extends Param {

	private static final long serialVersionUID = 91656872670088704L;
	
	@NotNull
	private long id;
	@Mobile
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String mobile;
	@Size(min = 2, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String nickname;
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String password;
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String cornette;
	@Size(min = 2,max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String uname;
}
