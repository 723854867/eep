package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum CompanyType implements IEnum<Integer> {
	
	// 使用单位
	USE(1),
	// 维保单位
	REPAIR(2);
	
	private int mark;
	
	private CompanyType(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
