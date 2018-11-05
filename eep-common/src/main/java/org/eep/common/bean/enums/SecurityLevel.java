package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum SecurityLevel implements IEnum<Integer> {
	
	// 正常
	NORMAL(1),
	// 警告
	WARN(2),
	// 待整改
	INTROSPECTING(3),
	// 严重超期
	FATAL(4);
	
	private int mark;
	
	private SecurityLevel(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
