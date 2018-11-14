package org.eep.bean.model;

import java.util.List;

import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.EmployeeInfo;
import org.eep.common.bean.model.RegionNode;
import org.eep.common.bean.model.UserInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrivilleges extends UserInfo {

	private static final long serialVersionUID = 3409702010270529575L;

	private List<EmployeeInfo> uses;
	private List<RegionNode> regions;
	private List<EmployeeInfo> repairs;
	
	public UserPrivilleges() {}
	
	public UserPrivilleges(User user) {
		super(user);
	}
}
