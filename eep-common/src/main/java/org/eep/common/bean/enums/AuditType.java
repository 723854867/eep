package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum AuditType implements IEnum<String> {
	
	// 初审
	TRIAL("01"),
	// 复审
	REVIEW("02");
	
	private String mark;
	
	private AuditType(String mark) {
		this.mark = mark;
	}

	@Override
	public String mark() {
		return this.mark;
	}
	
	public static final AuditType match(String mark) {
		switch (mark) {
		case "01":
			return AuditType.TRIAL;
		case "02":
			return AuditType.REVIEW;
		default:
			throw new RuntimeException("unrecognize operator cert audit type : " + mark);
		}
	}
}
