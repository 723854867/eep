package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum EmployeeState implements IEnum<Integer> {
	
	// 正常
	NORMAL(1),
	// 禁用
	FORBID(2),
	// 离职
	LEAVE(4);
	
	private int mark;
	
	private EmployeeState(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
