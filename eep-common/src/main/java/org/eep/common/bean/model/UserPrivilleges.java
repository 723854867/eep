package org.eep.common.bean.model;

import java.util.List;

import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrivilleges extends UserInfo {

	private static final long serialVersionUID = 3409702010270529575L;

	private List<RegionNode> regions;
	
	public UserPrivilleges() {}
	
	public UserPrivilleges(Visitor visitor) {
		super(visitor.getUser(), visitor.company(), visitor.getRegion());
	}
	
	public UserPrivilleges(User user, CompanyInfo company, SysRegion region) {
		super(user, company, region);
	}
}
