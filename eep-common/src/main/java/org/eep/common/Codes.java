package org.eep.common;

import org.rubik.bean.core.model.Code;

public interface Codes {

	// 地区已存在
	public static final Code REGION_EXIST				= Code.create("code.region.exist");
	// 法律法规不存在
	public static final Code LAW_NOT_EXIST				= Code.create("code.law.not.exist");
	// 登录密码错误
	public static final Code PWD_LOGIN_ERR				= Code.create("code.pwd.login.err");
	// 设备数量限制
	public static final Code DEVICE_MAXIMUM				= Code.create("code.device.maximum");
	// 用户名不存在
	public static final Code UNAME_NOT_EXIST			= Code.create("code.uname.not.exist");
	// 接口维护中
	public static final Code API_MAINTENANCE			= Code.create("code.api.maintenance");
	// 资源个数限制
	public static final Code RESOURCE_MAXIMUM			= Code.create("code.resource.maximum");
	// 设备不存在
	public static final Code DEVICE_NOT_EXIST			= Code.create("code.device.not.exist");
	// 不是企业员工
	public static final Code NOT_AN_EMPLOYEE			= Code.create("code.not.an.employee");
	// 地区不存在
	public static final Code REGION_NOT_EXIST			= Code.create("code.region.not.exist");
	// 检查记录不存在
	public static final Code INSPECT_NOT_EXIST			= Code.create("code.inspect.not.exist");
	// 单位不存在
	public static final Code COMPANY_NOT_EXIST			= Code.create("code.company.not.exist");
	// 用户已经拥有该地区权限
	public static final Code USER_REGION_EXIST			= Code.create("code.user.region.exist");
	// 地区层级限制
	public static final Code REGION_LAYER_LIMIT			= Code.create("code.region.layer.limit");
	// 雇员不存在
	public static final Code EMPLOYEE_NOT_EXIST			= Code.create("code.employee.not.exist");
	// 没有改地区权限
	public static final Code REGION_UNPERMISSION		= Code.create("code.region.unpermission");
	// 用户已经是该企业的雇员
	public static final Code EMPLOYEE_DUPLICATED		= Code.create("code.employee.duplicated");
	// 自查自纠不存在
	public static final Code INTROSPECT_NOT_EXIST		= Code.create("code.introspect.not.exist");
	// 设备数量超过限制
	public static final Code REPAIR_DEVICE_MAXIMUM		= Code.create("code.repair.device.maximum");
	// 法律法规类型不存在
	public static final Code LAW_CATEGORY_NOT_EXIST		= Code.create("code.law.category.not.exist");
	// 
	public static final Code REGION_CODE_UNRECOGNIZE	= Code.create("code.region.code.unrecognize");
	// 父级地区不存在
	public static final Code REGION_PARENT_NOT_EXIST	= Code.create("code.region.parent.not.exist");
	// 整改通知不存在
	public static final Code RECTIFY_NOTICE_NOT_EXIST	= Code.create("code.rectify.notice.not.exist");
	// 设备类型不存在
	public static final Code DEVICE_CATEGORY_NOT_EXIST	= Code.create("code.device.category.not.exist");
	// 设备状态同步中
	public static final Code DEVICE_ALERT_TASK_RUNNING	= Code.create("code.device.alert.task.running");
}
