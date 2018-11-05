package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eep.common.Consts;
import org.rubik.bean.core.enums.Client;
import org.rubik.bean.core.enums.Device;
import org.rubik.bean.core.enums.OS;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginParam extends Param {

	private static final long serialVersionUID = -4049100549979029221L;

	@NotNull
	private OS os;
	@NotEmpty
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String uname;
	@NotNull
	private Client client;
	@NotNull
	private Device device;
	@NotEmpty
	@Size(min = Consts.DEFAULT_MIN_PARAM_LEN, max = Consts.DEFAULT_MAX_PARAM_LEN)
	private String password;
}
