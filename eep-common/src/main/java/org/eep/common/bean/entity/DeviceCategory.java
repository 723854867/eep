package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceCategory implements Identifiable<String> {

	private static final long serialVersionUID = 7099396301937188379L;
	
	@Id
	private String code;
	private String name;

	@Override
	public String key() {
		return this.code;
	}
}
