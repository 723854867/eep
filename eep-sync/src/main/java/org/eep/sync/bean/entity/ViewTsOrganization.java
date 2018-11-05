package org.eep.sync.bean.entity;

import java.util.Date;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * 单位基本信息
 * 
 * @author lynn
 */
@Getter
@Setter
public class ViewTsOrganization implements Identifiable<String> {

	private static final long serialVersionUID = -7215246577357491557L;

	// 系统ID
	@Id
	private String sid;
	// 单位名称
	private String name;
	// 单位地址(company.address)
	private String address;
	// 组织机构代码(company.socialCreditCode)
	private String orgcode;
	// 行政区划代码
	private String areacode;
	// 联系人(company.contacts)
	private String contact;
	// 手机
	private String cellphone;
	// 电话
	private String telephone;
	// 传真
	private String fax;
	// 重点监控
	private String isemphasis;
	// 更新时间
	private Date updatedate;
	// 单位类型：1-使用单位、2-施工单位、3-维保单位
	private int orgtype;
	// 单位性质：1-企业、2-事业、3-机关、9-其他
	private String orgnature;
	// 经济类型：100-内资、200-港、澳、台投资、300-国外投资、900-其他
	private String ecocategories;
	// 法人代表(company.legalPersonName)
	private String legalperson;
	// 法人代表电话(company.legalPersonPhone)
	private String lptelephone;
	// 法人代表手机(company.legalPersonMobile)
	private String lpcellphone;
	
	@Override
	public String key() {
		return this.sid;
	}
}
