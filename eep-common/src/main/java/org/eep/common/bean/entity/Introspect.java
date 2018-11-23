package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Introspect implements Identifiable<Long> {

	private static final long serialVersionUID = -6930078078871952772L;
	
	@Id
	@GeneratedValue
	private long id;
	private String cid;
	private int created;
	// 兼职人数
	private int partTime;
	// 专职人数
	private int fullTime;
	private String filler;
	private String content;
	private String manager;
	private String address;
	// 在用安全阀数量
	private int safetyValve;
	// 持证人数
	private int certificater;
	// 是否有培训计划
	private boolean trainPlan;
	// 在用压力表数量
	private int pressureGauge;
	private String fillerPhone;
	// 法人
	private String legalPerson;
	// 管理员电话
	private String managerPhone;
	// 已校验并在有效期安全阀数量
	private int validSafetyValve;
	// 是否建立培训档案
	private boolean trainArchives;
	// 已校验并在有效期压力表数量
	private int validPressureGauge;
	// 自查项目序列化字段
	private String introspectItems;
	// 法人电话
	private String legalPersonPhone;
	// 管理机构
	private String managementAgency;
	// 是否制定特种设备安全管理制度
	private boolean safetyManageSystem;
	// 是否按规定维护
	private boolean regularMaintenance;
	// 是否建立安全技术档案
	private boolean safetyTechArchives;
	// 管理人员是否持证
	private boolean managerCertificated;
	// 事故应急救援情况说明
	private String accidentEmergencyDesc;
	// 安全管理制度情况说明
	private String safetyManageSystemDesc;
	// 是否制定事故应急救援预案
	private boolean accidentEmergencyPlan;
	// 事故应急救援预案是否演练
	private boolean accidentEmergencyDrill;

	@Override
	public Long key() {
		return this.id;
	}
}
