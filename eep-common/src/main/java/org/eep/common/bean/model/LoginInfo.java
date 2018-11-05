package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = -457709312840322749L;

	private String token;
	private UserInfo user;
	
	public LoginInfo(User user, UserToken token) {
		this.token = token.getToken();
		this.user = new UserInfo(user);
	}
}
