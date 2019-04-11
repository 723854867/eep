package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertInfo implements Serializable {

	private static final long serialVersionUID = 886045233752441847L;

	private long id;
	private String cid;
	private int created;
	private String cname;
	private String dname;
	private String certId;
	private AlertType type;
	private long rectifyId;
	private String deviceId;
	private String operatorId;
	private WarnLevel warnLevel;
	private String operatorName;
}
