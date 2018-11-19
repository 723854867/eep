package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersParam extends Param {

	private static final long serialVersionUID = -1832478802667861947L;

	@Min(1)
	private Long id;
	@Null
	private Long min;
	@Null
	private Long max;
	private String cid;
	@Min(1)
	private Long region;
	private String uname;
	private String cname;
	private String mobile;
	private String cornette;
	private String nickname;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
