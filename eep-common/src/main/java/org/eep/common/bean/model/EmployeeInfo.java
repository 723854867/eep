package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.EmployeeState;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeInfo implements Serializable {

	private static final long serialVersionUID = 8532811354602986168L;

	private long id;
	private long uid;
	private String cid;
	private long region;
	private String cname;
	private CompanyType ctype;
	private EmployeeState state;
}
