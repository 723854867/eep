package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Alert implements Identifiable<Long> {

	private static final long serialVersionUID = -1157664911812083834L;
	
	@Id
	@GeneratedValue
	private long id;
	private String cid;
	private int created;
	private String certId;
	private AlertType type;
	private long rectifyId;
	private String deviceId;
	private String operatorId;
	private WarnLevel warnLevel;
	
	@Override
	public Long key() {
		return this.id;
	}
}
