package org.eep.common.bean.param;

import org.eep.common.bean.enums.Sex;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorsParam extends Param {

	private static final long serialVersionUID = 4892087744929610814L;

	private Sex sex;
	private String id;
	private String cid;
	private String name;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
