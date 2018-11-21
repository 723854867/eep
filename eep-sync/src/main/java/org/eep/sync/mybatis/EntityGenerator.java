package org.eep.sync.mybatis;

import java.math.BigDecimal;

import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.LogExamine;
import org.eep.common.bean.entity.Operator;
import org.eep.common.bean.entity.OperatorCert;
import org.eep.common.bean.enums.AuditType;
import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.Sex;
import org.eep.sync.bean.entity.ViewTsCodeEqusortcode;
import org.eep.sync.bean.entity.ViewTsEquinspect;
import org.eep.sync.bean.entity.ViewTsEquipment;
import org.eep.sync.bean.entity.ViewTsOrganization;
import org.eep.sync.bean.entity.ViewTsPeroperator;
import org.eep.sync.bean.entity.ViewTsPeroperatorcert;
import org.rubik.util.common.StringUtil;

public class EntityGenerator {

	public static final Company newCompany(ViewTsOrganization temp) {
		Company instance = new Company();
		instance.setId(temp.getSid());
		instance.setLatitude(BigDecimal.ZERO);
		instance.setLongitude(BigDecimal.ZERO);
		switch (temp.getOrgtype()) {
		case 1:
			instance.setType(CompanyType.USE);
			break;
		case 3:
			instance.setType(CompanyType.REPAIR);
			break;
		default:
			throw new RuntimeException("error org type : " + temp.getOrgtype());
		}
		instance.setUpdated((int) (temp.getUpdatedate().getTime() / 1000));
		instance.setFax(StringUtil.hasText(temp.getFax()) ? temp.getFax() : StringUtil.EMPTY);
		instance.setName(StringUtil.hasText(temp.getName()) ? temp.getName() : StringUtil.EMPTY);
		instance.setAddress(StringUtil.hasText(temp.getAddress()) ? temp.getAddress() : StringUtil.EMPTY);
		instance.setContacts(StringUtil.hasText(temp.getContact()) ? temp.getContact() : StringUtil.EMPTY);
		instance.setAttr(StringUtil.hasText(temp.getOrgnature()) ? temp.getOrgnature() : StringUtil.EMPTY);
		instance.setSocialCreditCode(StringUtil.hasText(temp.getOrgcode()) ? temp.getOrgcode() : StringUtil.EMPTY);
		instance.setContactsPhone(StringUtil.hasText(temp.getCellphone()) ? temp.getCellphone() : StringUtil.EMPTY);
		instance.setLegalPersonName(StringUtil.hasText(temp.getLegalperson()) ? temp.getLegalperson() : StringUtil.EMPTY);
		instance.setLegalPersonPhone(StringUtil.hasText(temp.getLptelephone()) ? temp.getLptelephone() : StringUtil.EMPTY);
		instance.setLegalPersonMobile(StringUtil.hasText(temp.getLpcellphone()) ? temp.getLpcellphone() : StringUtil.EMPTY);
		return instance;
	}
	
	public static final Device newDevice(ViewTsEquipment temp) {
		Device instance = new Device();
		instance.setId(temp.getSid());
		instance.setCid(temp.getSidBaseorgmain());
		instance.setRegState(temp.getEquregstate());
		instance.setUseCerCode(temp.getUsecercode());
		instance.setInspectionAgency(temp.getCenname());
		instance.setUpdated((int) (temp.getUpdatedate().getTime() / 1000));
		instance.setDin(StringUtil.hasText(temp.getProcode()) ? temp.getProcode() : StringUtil.EMPTY);
		instance.setName(StringUtil.hasText(temp.getEquname()) ? temp.getEquname() : StringUtil.EMPTY);
		instance.setContact(StringUtil.hasText(temp.getContact()) ? temp.getContact() : StringUtil.EMPTY);
		instance.setLevel(StringUtil.hasText(temp.getEqulevel()) ? temp.getEqulevel() : StringUtil.EMPTY);
		instance.setModel(StringUtil.hasText(temp.getEqumodel()) ? temp.getEqumodel() : StringUtil.EMPTY);
		instance.setCode(StringUtil.hasText(temp.getEqusortname()) ? temp.getEqusortname() : StringUtil.EMPTY);
		instance.setAddress(StringUtil.hasText(temp.getEquaddress()) ? temp.getEquaddress() : StringUtil.EMPTY);
		instance.setAgeState(StringUtil.hasText(temp.getEqudepcase()) ? temp.getEqudepcase() : StringUtil.EMPTY);
		instance.setUseState(StringUtil.hasText(temp.getEquusestate()) ? temp.getEquusestate() : StringUtil.EMPTY);
		instance.setContactPhone(StringUtil.hasText(temp.getTelephone()) ? temp.getTelephone() : StringUtil.EMPTY);
		instance.setRegOrgName(StringUtil.hasText(temp.getRegorgname()) ? temp.getRegorgname() : StringUtil.EMPTY);
		instance.setEnrTabCode(StringUtil.hasText(temp.getEnrtabcode()) ? temp.getEnrtabcode() : StringUtil.EMPTY);
		instance.setIssCerDate(StringUtil.hasText(temp.getIsscerdate()) ? temp.getIsscerdate() : StringUtil.EMPTY);
		instance.setUseCerCode(StringUtil.hasText(temp.getUsecercode()) ? temp.getUsecercode() : StringUtil.EMPTY);
		instance.setOccasion(StringUtil.hasText(temp.getSuioccasion()) ? temp.getSuioccasion() : StringUtil.EMPTY);
		instance.setSafeMan(StringUtil.hasText(temp.getSafmanperson()) ? temp.getSafmanperson() : StringUtil.EMPTY);
		instance.setPlace(StringUtil.hasText(temp.getEquaddressint()) ? temp.getEquaddressint() : StringUtil.EMPTY);
		instance.setContactMobile(StringUtil.hasText(temp.getCellphone()) ? temp.getCellphone() : StringUtil.EMPTY);
		instance.setGuardLevel(StringUtil.hasText(temp.getKeywatlevel()) ? temp.getKeywatlevel() : StringUtil.EMPTY);
		instance.setUseEnv(StringUtil.hasText(temp.getUseenvironment()) ? temp.getUseenvironment() : StringUtil.EMPTY);
		instance.setDepartment(StringUtil.hasText(temp.getComdepartment()) ? temp.getComdepartment() : StringUtil.EMPTY);
		instance.setParAbstract(StringUtil.hasText(temp.getEquparabstract()) ? temp.getEquparabstract() : StringUtil.EMPTY);
		instance.setSafeManPhone(StringUtil.hasText(temp.getSafmancellphone()) ? temp.getSafmancellphone() : StringUtil.EMPTY);
		instance.setSafeManMobile(StringUtil.hasText(temp.getSafmantelephone()) ? temp.getSafmantelephone() : StringUtil.EMPTY);
		instance.setSafeDepartment(StringUtil.hasText(temp.getSafmandepartment()) ? temp.getSafmandepartment() : StringUtil.EMPTY);
		return instance;
	}
	
	public static final Operator newOperator(ViewTsPeroperator temp) {
		Operator instance = new Operator();
		instance.setId(temp.getSid());
		instance.setName(temp.getPername());
		instance.setIdentity(temp.getCernumber());
		instance.setCid(temp.getSidBaseorgmain());
		instance.setEducation(temp.getEduhistory());
		instance.setUpdated((int) (temp.getUpdatedate().getTime() / 1000));
		instance.setMobile(StringUtil.hasText(temp.getTel()) ? temp.getTel() : StringUtil.EMPTY);
		instance.setPhone(StringUtil.hasText(temp.getCellphone()) ? temp.getCellphone() : StringUtil.EMPTY);
		instance.setBirthday(StringUtil.hasText(temp.getBirthday()) ? temp.getBirthday() : StringUtil.EMPTY);
		instance.setAddress(StringUtil.hasText(temp.getConaddress()) ? temp.getConaddress() : StringUtil.EMPTY);
		switch (temp.getSex()) {
		case "0":
			instance.setSex(Sex.UNKNOWN);
			break;
		case "1":
			instance.setSex(Sex.MALE);
			break;
		case "2":
			instance.setSex(Sex.FEMALE);
			break;
		default:
			throw new RuntimeException("error operator sex : " + temp.getSex());
		}
		return instance;
	}
	
	public static final LogExamine newLogExamine(ViewTsEquinspect temp) {
		LogExamine instance = new LogExamine();
		instance.setId(temp.getSid());
		instance.setDeviceId(temp.getSidBaseequmain());
		instance.setNextTime(temp.getNextinsdate().getTime() / 1000);
		instance.setNextTime(Math.max(0, instance.getNextTime()));
		instance.setCreated((int) (temp.getInsdate().getTime() / 1000));
		instance.setUpdated((int) (temp.getUpdatedate().getTime() / 1000));
		instance.setRepNo(StringUtil.hasText(temp.getRepno()) ? temp.getRepno() : StringUtil.EMPTY);
		instance.setType(StringUtil.hasText(temp.getInstypecode()) ? temp.getInstypecode() : StringUtil.EMPTY);
		instance.setInspectionAgency(StringUtil.hasText(temp.getCenname()) ? temp.getCenname() : StringUtil.EMPTY);
		instance.setConclusion(StringUtil.hasText(temp.getInsverdict()) ? temp.getInsverdict() : StringUtil.EMPTY);
		instance.setExaminer(StringUtil.hasText(temp.getInsusername()) ? temp.getInsusername() : StringUtil.EMPTY);
		instance.setProblems(StringUtil.hasText(temp.getMajquestion()) ? temp.getMajquestion() : StringUtil.EMPTY);
		return instance;
	}
	
	public static final OperatorCert newOperatorCert(ViewTsPeroperatorcert temp, Operator operator) {
		OperatorCert instance = new OperatorCert();
		instance.setId(temp.getSid());
		instance.setCid(operator.getCid());
		instance.setState(temp.getState());
		instance.setType(temp.getWorktype());
		instance.setIssueAgency(temp.getIsscerorgname());
		instance.setOperatorId(temp.getSidBaseperoperator());
		instance.setAuditType(AuditType.match(temp.getApplycategory()));
		instance.setExpireTime((int) (temp.getExpdate().getTime() / 1000));
		instance.setUpdated((int) (temp.getUpdatedate().getTime() / 1000));
		instance.setApprovalTime((int) (temp.getPasdate().getTime() / 1000));
		instance.setLevel(StringUtil.hasText(temp.getWorklevel()) ? temp.getWorklevel() : StringUtil.EMPTY);
		instance.setCertno(StringUtil.hasText(temp.getStudentno()) ? temp.getStudentno() : StringUtil.EMPTY);
		instance.setWorkItems(StringUtil.hasText(temp.getWorkitem()) ? temp.getWorkitem() : StringUtil.EMPTY);
		instance.setExamAgency(StringUtil.hasText(temp.getExaorgname()) ? temp.getExaorgname() : StringUtil.EMPTY);
		instance.setAuthItems(StringUtil.hasText(temp.getAuthworkitem()) ? temp.getAuthworkitem() : StringUtil.EMPTY);
		return instance;
	}
	
	public static final DeviceCategory newDeviceCategory(ViewTsCodeEqusortcode code) {
		DeviceCategory instance = new DeviceCategory();
		instance.setCode(code.getCode());
		instance.setName(code.getName());
		return instance;
	}
}
