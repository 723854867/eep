package org.eep.common.bean.model;

import java.util.List;

import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.Resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectDetail extends InspectInfo {

	private static final long serialVersionUID = -3037732039123326821L;
	
	private String content;
	private List<Device> devices;
	private List<Resource> resources;
}
