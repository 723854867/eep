package org.eep.chuanglan.model;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class ChuangLanRequest implements Serializable {

	private static final long serialVersionUID = 8895809137070238498L;

	// 用户在253云通讯平台上申请的API账号
	private String account;
	// 用户在253云通讯平台上申请的API账号对应的API密钥
	private String password;
	
	public ChuangLanRequest account(String account) {
		this.account = account;
		return this;
	}
	
	public ChuangLanRequest password(String password) {
		this.password = password;
		return this;
	}
}
