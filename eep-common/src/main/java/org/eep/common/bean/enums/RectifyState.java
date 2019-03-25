package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum RectifyState implements IEnum<Integer> {
	
	// 新建
	NEWLY(1),
	// 已处理
	FINISHED(3);
	
	private int mark;
	
	private RectifyState(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
