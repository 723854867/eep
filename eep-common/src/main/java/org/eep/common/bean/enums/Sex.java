package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum Sex implements IEnum<Integer> {
	
	// 未知
	UNKNOWN(0),
	// 男性
	MALE(1),
	// 女性
	FEMALE(2);
	
	private int mark;
	
	private Sex(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
