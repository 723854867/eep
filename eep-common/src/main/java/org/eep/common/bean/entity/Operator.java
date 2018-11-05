package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.eep.common.bean.enums.Sex;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operator implements Identifiable<String> {

	private static final long serialVersionUID = -1969930352578854005L;
	
	// 性别
	private Sex sex;
	@Id
	private String id;
	// 使用单位编号
	private String cid;
	// 姓名
	private String name;
	// 更新时间
	private int updated;
	// 联系电话
	private String phone;
	// 手机号码
	private String mobile;
	// 联系地址
	private String address;
	// 身份证件号码
	private String identity;
	// 出生年月
	private String birthday;
	// 学历
	private String education;

	@Override
	public String key() {
		return this.id;
	}
}
