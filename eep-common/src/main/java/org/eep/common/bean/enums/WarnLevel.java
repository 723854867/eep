package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum WarnLevel implements IEnum<Integer> {
	
	GREEN(0),
	YELLOW(1),
	BLUE(2),
	RED(3);
	
	private int mark;
	
	private WarnLevel(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
