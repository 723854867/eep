package org.eep.common.bean.param;

import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.eep.common.bean.model.IntrospectItem;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntrospectCreateParam extends Param {

	private static final long serialVersionUID = -494372394640199356L;

	@Min(0)
	private int partTime;
	@Min(0)
	private int fullTime;
	@NotEmpty
	private String filler;
	private String manager;
	private String address;
	private int safetyValve;
	@Min(0)
	private int certificater;
	private boolean trainPlan;
	private int pressureGauge;
	@NotEmpty
	private String fillerPhone;
	@NotEmpty
	private String legalPerson;
	private String managerPhone;
	private int validSafetyValve;
	private boolean trainArchives;
	private int validPressureGauge;
	@NotEmpty
	private String legalPersonPhone;
	private String managementAgency;
	private boolean safetyManageSystem;
	private boolean regularMaintenance;
	private boolean safetyTechArchives;
	private boolean managerCertificated;
	private String accidentEmergencyDesc;
	private String safetyManageSystemDesc;
	private boolean accidentEmergencyPlan;
	private boolean accidentEmergencyDrill;
	@NotEmpty
	@Size(max = 20)
	private Map<String, IntrospectItem> items;
}
