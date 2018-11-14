package org.eep.common.bean.param;

import org.eep.common.bean.enums.CompanyType;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeesParam extends Param {

	private static final long serialVersionUID = -4668997760303860657L;

	private Long id;
	private Long uid;
	private String cid;
	private String name;
	private CompanyType type;
}
