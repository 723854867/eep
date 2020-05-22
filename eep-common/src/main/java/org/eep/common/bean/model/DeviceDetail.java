package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDetail implements Serializable {

	private static final long serialVersionUID = 3603573700421287507L;

	private String din;
	private Long nextTime;
	private String time;
	private String name;
	private String cname;
	private WarnLevel warnLevel;
	
}
