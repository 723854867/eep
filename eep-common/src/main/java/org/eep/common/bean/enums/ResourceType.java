package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum ResourceType implements IEnum<Integer> {
	
	// 设备维保记录图
	DEVICE_REPAIR(1),
	// 企业自查自纠文件
	COMPANY_INTROSPECT(2);
	
	private int mark;
	
	private ResourceType(int mark) {
		this.mark = mark;
	}

	@Override
	public Integer mark() {
		return this.mark;
	}
}
