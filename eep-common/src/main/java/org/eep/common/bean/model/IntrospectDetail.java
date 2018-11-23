package org.eep.common.bean.model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.Resource;
import org.rubik.util.serializer.GsonSerializer;

import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntrospectDetail implements Serializable {

	private static final long serialVersionUID = 6894489713945778691L;
	
	private static final Type ITEM = new TypeToken<Map<String, IntrospectItem>>() {}.getType();

	private long id;
	private String cid;
	private int created;
	private int partTime;
	private int fullTime;
	private String filler;
	private String manager;
	private String address;
	private String content;
	private int safetyValve;
	private int certificater;
	private boolean trainPlan;
	private int pressureGauge;
	private String fillerPhone;
	private String legalPerson;
	private String managerPhone;
	private int validSafetyValve;
	private boolean trainArchives;
	private int validPressureGauge;
	private String legalPersonPhone;
	private String managementAgency;
	private List<Resource> resources;
	private boolean safetyManageSystem;
	private boolean regularMaintenance;
	private boolean safetyTechArchives;
	private boolean managerCertificated;
	private String accidentEmergencyDesc;
	private String safetyManageSystemDesc;
	private boolean accidentEmergencyPlan;
	private boolean accidentEmergencyDrill;
	private Map<String, IntrospectItem> items;
	
	public IntrospectDetail() {}
	
	public IntrospectDetail (Introspect introspect) {
		this.id = introspect.getId();
		this.cid = introspect.getCid();
		this.content = introspect.getContent();
		this.filler = introspect.getFiller();
		this.created = introspect.getCreated();
		this.manager = introspect.getManager();
		this.address = introspect.getAddress();
		this.partTime = introspect.getPartTime();
		this.fullTime = introspect.getFullTime();
		this.safetyValve = introspect.getSafetyValve();
		this.certificater = introspect.getCertificater();
		this.trainPlan = introspect.isTrainPlan();
		this.pressureGauge = introspect.getPressureGauge();
		this.fillerPhone = introspect.getFillerPhone();
		this.legalPerson = introspect.getLegalPerson();
		this.managerPhone = introspect.getManagerPhone();
		this.validSafetyValve = introspect.getValidSafetyValve();
		this.trainArchives = introspect.isTrainArchives();
		this.validPressureGauge = introspect.getValidPressureGauge();
		this.legalPersonPhone = introspect.getLegalPersonPhone();
		this.managementAgency = introspect.getManagementAgency();
		this.safetyManageSystem = introspect.isSafetyManageSystem();
		this.regularMaintenance = introspect.isRegularMaintenance();
		this.safetyTechArchives = introspect.isSafetyTechArchives();
		this.managerCertificated = introspect.isManagerCertificated();
		this.accidentEmergencyDesc = introspect.getAccidentEmergencyDesc();
		this.safetyManageSystemDesc = introspect.getSafetyManageSystemDesc();
		this.accidentEmergencyPlan = introspect.isAccidentEmergencyPlan();
		this.accidentEmergencyDrill = introspect.isAccidentEmergencyDrill();
		this.items = GsonSerializer.fromJson(introspect.getIntrospectItems(), ITEM);
	}
}
