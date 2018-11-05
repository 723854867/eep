package org.eep.common;

import org.rubik.bean.core.model.ConfigKey;

public interface Consts {

	final int DEFAULT_MIN_PARAM_LEN					= 6;
	final int DEFAULT_MAX_PARAM_LEN					= 50;
	// 超级管理员用户编号
	final ConfigKey<Long> ROOT_UID					= ConfigKey.<Long>create("root_uid", Long.class);
	// 服务器安全等级
	final ConfigKey<Integer> SERVER_SECURITY_LEVEL	= ConfigKey.<Integer>create("server_security_level", 1, Integer.class);
	// 单挑维保记录允许的最大设备数
	final ConfigKey<Integer> DEVICE_REPAIR_MAXIMUM	= ConfigKey.<Integer>create("device_repair_maximum", 20, Integer.class);
}
