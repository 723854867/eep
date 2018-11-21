package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevicesParam extends Param {

	private static final long serialVersionUID = -2064641284596312744L;

	@Null
	private Long min;
	@Null
	private Long max;
	private String id;
	private String cid;
	private String name;
	@Min(1)
	private Long region;
	private String code;
	private String cname;
	private String model;
	private WarnLevel warnLevel;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
