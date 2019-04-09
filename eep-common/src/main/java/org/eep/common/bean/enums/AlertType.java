package org.eep.common.bean.enums;

import org.rubik.bean.core.enums.IEnum;

public enum AlertType implements IEnum<Integer> {

	// 获取不到设备检测日期
	EXAMINE_DATE_NILL(1),
	// 检测日期轻度超限
	EXAMINE_DATE_EXPIRE_LIGHT(2),
	// 检测日期重度超限
	EXAMINE_DATE_EXPIRE_SERIOUS(3),
	// 整改通知
	RECITIFY_NOTICE(4),
	// 整改通知超期
	RECITIFY_NOTICE_EXPIRE(5),
	// 作业人员证书有效期轻度超期
	OPERATOR_CERT_EXPIRE_LIGHT(7),
	// 作业人员证书有效期重度超期
	OPERATOR_CERT_EXPIRE_SERIOUS(8);
	
	private int mark;
	
	private AlertType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public Integer mark() {
		return this.mark;
	}
}
