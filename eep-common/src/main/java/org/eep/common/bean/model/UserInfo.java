package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2840599649719021186L;

	private long id;
	private int created;
	private int updated;
	private String uname;
	private String avatar;
	private String nickname;
	
	public UserInfo() {}
	
	public UserInfo(User user) {
		this.id = user.getId();
		this.uname = user.getUname();
		this.avatar = user.getAvatar();
		this.created = user.getCreated();
		this.updated = user.getUpdated();
		this.nickname = user.getNickname();
	}
}
