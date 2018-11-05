package org.eep.sync.bean.entity;

import java.util.Date;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * 作业人员信息
 * 
 * @author lynn
 */
@Getter
@Setter
public class ViewTsPeroperator implements Identifiable<String> {

	private static final long serialVersionUID = -838367718650045573L;
	
	@Id
	private String sid;
	// 所在单位系统ID
	private String sidBaseorgmain;
	// 姓名
	private String pername;
	// 身份证件号码
	private String cernumber;
	// 所在地区代码
	private String perareacode;
	// 出生年月
	private String birthday;
	// 性别
	private String sex;
	// 学历
	private String eduhistory;
	// 联系地址
	private String conaddress;
	// 联系电话
	private String tel;
	// 手机号码
	private String cellphone;
	// 更新时间
	private Date updatedate;

	@Override
	public String key() {
		return this.sid;
	}
}
