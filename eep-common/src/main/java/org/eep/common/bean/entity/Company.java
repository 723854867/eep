package org.eep.common.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.eep.common.bean.enums.CompanyType;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company implements Identifiable<String> {

	private static final long serialVersionUID = -3094652257788608278L;
	
	@Id
	private String id;
	private String fax;
	// 地区编号
	private long region;
	// 企业性质
	private String attr;
	private int updated;
	// 企业名
	private String name;
	// 企业地址
	private String address;
	// 企业联系人
	private String contacts;
	// 企业类型
	private CompanyType type;
	// 地理位置(纬度)
	private BigDecimal latitude;
	// 地理位置(经度)
	private BigDecimal longitude;
	// 企业联系人电话
	private String contactsPhone;
	// 法人姓名
	private String legalPersonName;
	// 社会信用代码(五证合一)
	private String socialCreditCode;
	// 法人电话
	private String legalPersonPhone;
	// 法人手机
	private String legalPersonMobile;

	@Override
	public String key() {
		return this.id;
	}
}
