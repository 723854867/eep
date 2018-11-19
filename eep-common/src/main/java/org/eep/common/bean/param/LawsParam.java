package org.eep.common.bean.param;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawsParam extends Param {

	private static final long serialVersionUID = 6495191565276607484L;

	private String content;
	private Integer categoryId;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
