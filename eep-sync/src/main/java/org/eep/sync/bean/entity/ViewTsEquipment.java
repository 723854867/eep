package org.eep.sync.bean.entity;

import java.util.Date;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewTsEquipment implements Identifiable<String> {

	private static final long serialVersionUID = 1370347935114435646L;
	
	@Id
	// 系统ID(device.id)
	private String sid;
	// 设备代码
	private String equcode;
	// 设备注册代码
	private String equregcode;
	// 使用单位ID(device.useCid)
	private String sidBaseorgmain;
	// 设备所在地址(device.address)
	private String equaddress;
	// 设备所在地点(内部)(device.place)
	private String equaddressint;
	// 设备所在地区代码
	private String equareacode;
	// 主管部门(device.department)
	private String comdepartment;
	// 联系人(device.contact)
	private String contact;
	// 联系人电话(device.contactPhone)
	private String telephone;
	// 联系人手机(device.contactMobile)
	private String cellphone;
	// 安全管理部门(device.safeDepartment)
	private String safmandepartment;
	// 安全管理人员(device.safeMan)
	private String safmanperson;
	// 安管人员电话(device.safeManPhone)
	private String safmantelephone;
	// 安管人员手机(device.safeManMobile)
	private String safmancellphone;
	// 设备类别代码
	private String equsortcode;
	// 设备类别名称(device.category)
	private String equsortname;
	// 设备内部编号
	private String equintcode;
	// 设备名称(device.name)
	private String equname;
	// 设备型号(device.model)
	private String equmodel;
	// 出厂编号(device.din)
	private String procode;
	// 适用场合(device.occasion)
	private String suioccasion;
	// 使用环境(device.useEnv)
	private String useenvironment;
	// 设备级别(device.level)
	private String equlevel;
	// 安全级别(device.safeLevel)
//	private String saflevel;
	// 设备新旧状况(device.ageState)
	private String equdepcase;
	// 重点监控等级：1-重点监控；A-一级监控；B-二级重点监控；C-三级重点监控(device.guardLevel)
	private String keywatlevel;
	// 设备使用状态：1-在用；2-停用；7-中止；8-流程中；9-注销(device.useState)
	private String equusestate;
	// 设备注册状态：1-已登记；0-未登记；9-注销(device.regState)
	private String equregstate;
	// 设备主要参数摘要(device.parAbstract)
	private String equparabstract;
	// 检验单位名称(device.repiarCompany)
	private String cenname;
	// 更新时间(device.updated)
	private Date updatedate;
	// 注册登记机构(device.regOrgName)
	private String regorgname;
	// 登记表编号(device.enrTabCode)
	private String enrtabcode;
	// 使用证编号(device.useCerCode)
	private String usecercode;
	// 发证日期(device.issCerDate)
	private String isscerdate;

	@Override
	public String key() {
		return this.sid;
	}
}
