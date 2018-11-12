package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompaniesParam extends Param {

	private static final long serialVersionUID = 7435855527033773205L;

	@Min(1)
	private Long id;
	@Null
	private Long min;
	@Null
	private Long max;
	@Min(1)
	private Long region;
	private String name;
	private CompanyType type;
	private WarnLevel warnLevel;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
