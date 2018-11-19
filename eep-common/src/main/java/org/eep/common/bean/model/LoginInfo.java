package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = -457709312840322749L;

	private String token;
	private UserPrivilleges user;
	
	public LoginInfo(User user, Company company, SysRegion region, UserToken token) {
		this.token = token.getToken();
		this.user = new UserPrivilleges(user, company, region);
	}
}
