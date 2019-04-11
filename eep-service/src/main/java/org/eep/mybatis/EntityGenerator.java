package org.eep.mybatis;

import org.apache.commons.codec.digest.DigestUtils;
import org.eep.common.bean.entity.Alert;
import org.eep.common.bean.entity.CompanyCustom;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.Law;
import org.eep.common.bean.entity.LawCategory;
import org.eep.common.bean.entity.RectifyNotice;
import org.eep.common.bean.entity.Repair;
import org.eep.common.bean.entity.RepairDevice;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserToken;
import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.RectifyState;
import org.eep.common.bean.enums.ResourceType;
import org.eep.common.bean.enums.WarnLevel;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.CompanyModifyParam;
import org.eep.common.bean.param.IntrospectCreateParam;
import org.eep.common.bean.param.LawCategoryCreateParam;
import org.eep.common.bean.param.LawCreateParam;
import org.eep.common.bean.param.LoginParam;
import org.eep.common.bean.param.RectifyNoticeCreateParam;
import org.eep.common.bean.param.RegionCreateParam;
import org.eep.common.bean.param.UserCreateParam;
import org.rubik.util.common.DateUtil;
import org.rubik.util.common.KeyUtil;
import org.rubik.util.common.StringUtil;
import org.rubik.util.serializer.GsonSerializer;

public class EntityGenerator {

	public static final User newUser(UserCreateParam param) {
		User instance = new User();
		instance.setUname(param.getUname());
		instance.setAvatar(StringUtil.EMPTY);
		instance.setMobile(param.getMobile());
		instance.setNickname(param.getNickname());
		instance.setSalt(KeyUtil.randomCode(6, false));
		instance.setPwd(DigestUtils.md5Hex(param.getPassword() + "_" + instance.getSalt()));
		instance.setCornette(StringUtil.hasText(param.getCornette()) ? param.getCornette() : StringUtil.EMPTY);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final UserToken newUserToken(User user, LoginParam param) {
		UserToken instance = new UserToken();
		instance.setOs(param.getOs());
		instance.setUid(user.getId());
		instance.setToken(KeyUtil.uuid());
		instance.setUname(param.getUname());
		instance.setClient(param.getClient());
		instance.setDevice(param.getDevice());
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final SysRegion newSysRegion(SysRegion parent, int left, RegionCreateParam param, long id) {
		SysRegion instance = new SysRegion();
		instance.setId(id);
		instance.setLeft(left);
		instance.setCode(param.getCode());
		instance.setName(param.getName());
		instance.setRight(instance.getLeft() + 1);
		instance.setOpen(null == parent ? false : parent.isOpen());
		instance.setLayer(null == parent ? 1 : parent.getLayer() + 1);
		instance.setTrunk(null == parent ? KeyUtil.timebasedId() : parent.getTrunk());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final Repair newRepair(String cid, String rid, int nextTime, String content, long committer) {
		Repair instance = new Repair();
		instance.setCid(cid);
		instance.setRid(rid);
		instance.setNextTime(nextTime);
		instance.setCommitter(committer);
		instance.setCreated(DateUtil.current());
		instance.setContent(StringUtil.hasText(content) ? content : StringUtil.EMPTY);
		return instance;
	}
	
	public static final RepairDevice newRepairDevice(long repairId, String deviceId) {
		RepairDevice instance = new RepairDevice();
		instance.setDeviceId(deviceId);
		instance.setRepairId(repairId);
		return instance;
	}
	
	public static final Resource newResource(long bytes, String url, String path, String name, ResourceType type, String owner, int priority) {
		Resource instance = new Resource();
		instance.setUrl(url);
		instance.setType(type);
		instance.setName(name);
		instance.setPath(path);
		instance.setBytes(bytes);
		instance.setOwner(owner);
		instance.setPriority(priority);
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final Introspect newIntrospect(IntrospectCreateParam param) {
		Introspect instance = new Introspect();
		Visitor visitor = param.requestor();
		instance.setFiller(param.getFiller());
		instance.setCreated(DateUtil.current());
		instance.setPartTime(param.getPartTime());
		instance.setFullTime(param.getFullTime());
		instance.setTrainPlan(param.isTrainPlan());
		instance.setCid(visitor.getCompany().getId());
		instance.setLegalPerson(param.getLegalPerson());
		instance.setSafetyValve(param.getSafetyValve());
		instance.setFillerPhone(param.getFillerPhone());
		instance.setCertificater(param.getCertificater());
		instance.setTrainArchives(param.isTrainArchives());
		instance.setPressureGauge(param.getPressureGauge());
		instance.setLegalPersonPhone(param.getLegalPersonPhone());
		instance.setValidSafetyValve(param.getValidSafetyValve());
		instance.setRegularMaintenance(param.isRegularMaintenance());
		instance.setSafetyManageSystem(param.isSafetyManageSystem());
		instance.setSafetyTechArchives(param.isSafetyTechArchives());
		instance.setValidPressureGauge(param.getValidPressureGauge());
		instance.setManagerCertificated(param.isManagerCertificated());
		instance.setAccidentEmergencyPlan(param.isAccidentEmergencyDrill());
		instance.setAccidentEmergencyDrill(param.isAccidentEmergencyDrill());
		instance.setIntrospectItems(GsonSerializer.toJson(param.getItems()));
		instance.setContent(StringUtil.hasText(param.getContent()) ? param.getContent() : StringUtil.EMPTY);
		instance.setAddress(StringUtil.hasText(param.getAddress()) ? param.getAddress() : StringUtil.EMPTY);
		instance.setManager(StringUtil.hasText(param.getManager()) ? param.getManager() : StringUtil.EMPTY);
		instance.setManagerPhone(StringUtil.hasText(param.getManagerPhone()) ? param.getManagerPhone() : StringUtil.EMPTY);
		instance.setManagementAgency(StringUtil.hasText(param.getManagementAgency()) ? param.getManagementAgency() : StringUtil.EMPTY);
		instance.setAccidentEmergencyDesc(StringUtil.hasText(param.getAccidentEmergencyDesc()) ? param.getAccidentEmergencyDesc() : StringUtil.EMPTY);
		instance.setSafetyManageSystemDesc(StringUtil.hasText(param.getSafetyManageSystemDesc()) ? param.getSafetyManageSystemDesc() : StringUtil.EMPTY);
		return instance;
	}
	
	public static final DeviceCategory newDeviceCategory(CategoryParam param) {
		DeviceCategory instance = new DeviceCategory();
		instance.setCode(param.getCode());
		instance.setName(param.getName());
		return instance;
	}
	
	public static final Alert newAlert(String cid, AlertType type, WarnLevel warnLevel, String deviceId) {
		Alert instance = new Alert();
		instance.setCid(cid);
		instance.setType(type);
		instance.setDeviceId(deviceId);
		instance.setWarnLevel(warnLevel);
		instance.setCertId(StringUtil.EMPTY);
		instance.setOperatorId(StringUtil.EMPTY);
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final Alert newAlert(String cid, AlertType type, WarnLevel warnLevel, long rectifyId) {
		Alert instance = new Alert();
		instance.setCid(cid);
		instance.setType(type);
		instance.setRectifyId(rectifyId);
		instance.setWarnLevel(warnLevel);
		instance.setCertId(StringUtil.EMPTY);
		instance.setDeviceId(StringUtil.EMPTY);
		instance.setOperatorId(StringUtil.EMPTY);
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final Alert newAlert(String cid, AlertType type, WarnLevel warnLevel, String certId, String operatorId) {
		Alert instance = new Alert();
		instance.setCid(cid);
		instance.setType(type);
		instance.setCertId(certId);
		instance.setWarnLevel(warnLevel);
		instance.setOperatorId(operatorId);
		instance.setDeviceId(StringUtil.EMPTY);
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final RectifyNotice newRectifyNoticeCreate(RectifyNoticeCreateParam param) {
		RectifyNotice instance = new RectifyNotice();
		instance.setCid(param.getCid());
		instance.setProblem(param.getProblem());
		instance.setMeasure(param.getMeasure());
		instance.setDeregulation(param.getDeregulation());
		instance.setProcessBasis(param.getProcessBasis());
		instance.setCommitter(param.requestor().id());
		instance.setClosingTime(param.getClosingTime());
		instance.setState(RectifyState.NEWLY);
		instance.setWarnLevel(param.getWarnLevel());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final LawCategory newLawCategory(LawCategoryCreateParam param) {
		LawCategory instance = new LawCategory();
		instance.setName(param.getName());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final Law newLaw(LawCreateParam param) {
		Law instance = new Law();
		instance.setTitle(param.getTitle());
		instance.setContent(param.getContent());
		instance.setCategoryId(param.getCategoryId());
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final Inspect newInspect(String cid, long time, String content, long committer) {
		Inspect instance = new Inspect();
		instance.setCid(cid);
		instance.setTime(time);
		instance.setCommitter(committer);
		instance.setCreated(DateUtil.current());
		instance.setContent(StringUtil.hasText(content) ? content : StringUtil.EMPTY);
		return instance;
	}
	
	public static final CompanyCustom newCompanyCustom(CompanyModifyParam param) {
		CompanyCustom instance = new CompanyCustom();
		instance.setId(param.getId());
		instance.setMemo(param.getMemo());
		return instance;
	}
}
