package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Device implements Identifiable<String> {

	private static final long serialVersionUID = 3827854003372599875L;
	
	@Id
	private String id;
	// 使用单位编号
	private String cid;
	// 出厂编号din码(device identification number)
	private String din;
	private int updated;
	// 设备类别
	private String code;
	// 设备名称
	private String name;
	// 设备级别
	private String level;
	// 设备所在地点(内部)
	private String place;
	// 设备型号
	private String model;
	// 使用环境
	private String useEnv;
	// 联系人
	private String contact;
	// 安全管理人员
	private String safeMan;
	// 设备所在地址
	private String address;
	// 设备使用状态
	private String useState;
	// 设备注册状态
	private String regState;
	// 设备新旧状况
	private String ageState;
	// 适用场合
	private String occasion;
	// 安全级别
	private String safeLevel;
	// 注册登记机构
	private String regOrgName;
	// 登记表编号
	private String enrTabCode;
	// 发证日期
	private String issCerDate;
	// 使用证编号
	private String useCerCode;
	// 主管部门
	private String department;
	// 重点监控等级
	private String guardLevel;
	// 设备主要参数摘要
	private String parAbstract;
	// 安全管理人员电话
	private String safeManPhone;
	// 联系人电话
	private String contactPhone;
	// 联系人手机
	private String contactMobile;
	// 安全管理人员手机
	private String safeManMobile;
	// 安全管理部门
	private String safeDepartment;
	// 检验机构
	private String inspectionAgency;

	@Override
	public String key() {
		return this.id;
	}
}
