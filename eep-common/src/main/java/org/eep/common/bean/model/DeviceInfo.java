package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = 3603573700421287507L;

	private String id;
	private String cid;
	private String din;
	private String code;
	private int updated;
	private String name;
	private String cname;
	private String level;
	private String place;
	private String model;
	private String useEnv;
	private String contact;
	private String safeMan;
	private String address;
	private String useState;
	private String regState;
	private String ageState;
	private String occasion;
	private String category;
	private String safeLevel;
	private String regOrgName;
	private String enrTabCode;
	private String issCerDate;
	private String useCerCode;
	private String department;
	private String guardLevel;
	private String parAbstract;
	private WarnLevel warnLevel;
	private String safeManPhone;
	private String contactPhone;
	private String contactMobile;
	private String safeManMobile;
	private String safeDepartment;
	private String inspectionAgency;
}
