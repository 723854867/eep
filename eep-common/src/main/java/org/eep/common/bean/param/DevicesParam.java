package org.eep.common.bean.param;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevicesParam extends Param {

	private static final long serialVersionUID = -2064641284596312744L;

	private String id;
	private String cid;
	private String name;
	private String code;
	private String cname;
	private String model;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
