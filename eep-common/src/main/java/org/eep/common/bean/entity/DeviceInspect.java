package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceInspect implements Identifiable<Long>{

	private static final long serialVersionUID = 5617721596004981989L;
	@Id
	@GeneratedValue
	private Long id;
	private String deviceId;
	private String deviceName;
	private String deviceCode;
	private String contact;
	private String contactPhone;
	private String address;
	private String companyId;
	private String companyName;
	private Long uid;
	private String username;
	private Long nextTime;
	private String useState;
	private Integer created;
	@Override
	public Long key() {
		return this.id;
	}

}
