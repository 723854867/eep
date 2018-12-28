package org.eep.common.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInfo implements Serializable {

	private static final long serialVersionUID = -8043309694871488680L;

	private String id;
	private String fax;
	private long region;
	private String attr;
	private int updated;
	private String name;
	private String memo;
	private int deviceNum;
	private String address;
	private String contacts;
	private CompanyType type;
	private String regionName;
	private WarnLevel warnLevel;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String contactsPhone;
	private String legalPersonName;
	private String socialCreditCode;
	private String legalPersonPhone;
	private String legalPersonMobile;
}
