package org.eep.common.bean.param;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntrospectParam extends Param {

	private static final long serialVersionUID = -7536352372805893719L;
	
	private String cid;
	private String name;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
