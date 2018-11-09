package org.eep.mybatis;

import org.apache.commons.codec.digest.DigestUtils;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Alert;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.Employee;
import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.entity.InspectDevice;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.RectifyNotice;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserRegion;
import org.eep.common.bean.entity.UserToken;
import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.EmployeeState;
import org.eep.common.bean.enums.RectifyState;
import org.eep.common.bean.enums.ResourceType;
import org.eep.common.bean.enums.WarnLevel;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.IntrospectCreateParam;
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
		instance.setNickname(param.getNickname());
		instance.setSalt(KeyUtil.randomCode(6, false));
		instance.setPwd(DigestUtils.md5Hex(param.getPassword() + "_" + instance.getSalt()));
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
	
	public static final UserRegion newUserRegion(long uid, SysRegion region) {
		UserRegion instance = new UserRegion();
		instance.setUid(uid);
		instance.setRegion(region.getId());
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final Employee newEmployee(User user, Company company) {
		Employee instance = new Employee();
		instance.setUid(user.getId());
		instance.setCid(company.getId());
		instance.setState(EmployeeState.NORMAL);
		int time = DateUtil.current();
		instance.setCreated(time);
		instance.setUpdated(time);
		return instance;
	}
	
	public static final Inspect newInspect(String cid, String rid, String content, long committer) {
		Inspect instance = new Inspect();
		instance.setCid(cid);
		instance.setRid(rid);
		instance.setCommitter(committer);
		instance.setCreated(DateUtil.current());
		instance.setContent(StringUtil.hasText(content) ? content : StringUtil.EMPTY);
		return instance;
	}
	
	public static final InspectDevice newInspectDevice(long inspectId, String deviceId) {
		InspectDevice instance = new InspectDevice();
		instance.setDeviceId(deviceId);
		instance.setInspectId(inspectId);
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
		instance.setCreated(DateUtil.current());
		return instance;
	}
	
	public static final Alert newAlert(String cid, AlertType type, WarnLevel warnLevel, long rectifyId) {
		Alert instance = new Alert();
		instance.setCid(cid);
		instance.setType(type);
		instance.setRectifyId(rectifyId);
		instance.setWarnLevel(warnLevel);
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
}
