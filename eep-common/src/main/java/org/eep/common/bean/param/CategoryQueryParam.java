package org.eep.common.bean.param;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryQueryParam extends Param {

	private static final long serialVersionUID = 1050875554237481371L;

	private String code;
	private String name;
	
	@Override
	public void verify() {
		super.verify();
	}
}
