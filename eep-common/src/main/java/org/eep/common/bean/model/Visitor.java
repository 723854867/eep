package org.eep.common.bean.model;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserToken;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Requestor;

public class Visitor implements Requestor {
	
	private User user;
	private String lockId;
	private Company company;
	private UserToken token;
	private SysRegion region;
	
	public Visitor(User user, UserToken token) {
		this.user = user;
		this.token = token;
	}
	
	public Visitor(String lockId, User user, UserToken token) {
		this.user = user;
		this.token = token;
		this.lockId = lockId;
	}

	@Override
	public long id() {
		return this.user.getId();
	}

	@Override
	public String name() {
		return this.user.getNickname();
	}
	
	public User getUser() {
		return user;
	}
	
	public String getLockId() {
		return lockId;
	}
	
	public UserToken getToken() {
		return token;
	}
	
	public SysRegion getRegion() {
		return region;
	}
	
	public Company company() {
		return this.company;
	}
	
	public Company getCompany() {
		return Assert.notNull(company, Codes.NOT_AN_EMPLOYEE);
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setRegion(SysRegion region) {
		this.region = region;
	}
}
