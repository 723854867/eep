package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.enums.CompanyType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo implements Serializable {

	private static final long serialVersionUID = -2840599649719021186L;

	private long id;
	private String cid;
	private long region;
	private int created;
	private int updated;
	private String cname;
	private String uname;
	private String avatar;
	private String mobile;
	private String cornette;
	private String nickname;
	private String regionName;
	private CompanyType ctype;
	
	public UserInfo() {}
	
	public UserInfo(User user) {
		this.id = user.getId();
		this.uname = user.getUname();
		this.mobile = user.getMobile();
		this.avatar = user.getAvatar();
		this.created = user.getCreated();
		this.updated = user.getUpdated();
		this.nickname = user.getNickname();
		this.cornette = user.getCornette();
	}
	
	public UserInfo(User user, CompanyInfo company, SysRegion region) {
		this.id = user.getId();
		this.uname = user.getUname();
		this.mobile = user.getMobile();
		this.avatar = user.getAvatar();
		this.created = user.getCreated();
		this.updated = user.getUpdated();
		this.nickname = user.getNickname();
		this.cornette = user.getCornette();
		if (null != company) {
			this.cid = company.getId();
			this.cname = company.getName();
			this.ctype = company.getType();
		}
		if (null != region) {
			this.region = region.getId();
			this.regionName = region.getName();
		}
	}
}
