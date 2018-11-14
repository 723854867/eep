package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairDevice implements Identifiable<Long> {

	private static final long serialVersionUID = 7519732102518874419L;
	
	@Id
	@GeneratedValue
	private long id;
	private long repairId;
	private String deviceId;

	@Override
	public Long key() {
		return this.id;
	}
}
