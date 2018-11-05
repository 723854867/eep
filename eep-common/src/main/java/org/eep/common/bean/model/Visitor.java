package org.eep.common.bean.model;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Employee;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserToken;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Requestor;

public class Visitor implements Requestor {
	
	private User user;
	private String lockId;
	private Company company;
	private UserToken token;
	private Employee employee;
	
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
	
	public Company getCompany() {
		return Assert.notNull(company, Codes.EMPLOYEE_ID_MISS);
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Employee getEmployee() {
		return Assert.notNull(employee, Codes.EMPLOYEE_ID_MISS);
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
