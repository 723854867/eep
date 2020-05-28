package org.eep.mybatis.dao;

import java.util.List;

import javax.validation.Valid;

import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceInspect;
import org.eep.common.bean.model.DeviceDetail;
import org.eep.common.bean.model.DeviceInfo;
import org.eep.common.bean.param.DeviceInspectParam;
import org.eep.common.bean.param.DevicesParam;
import org.rubik.mybatis.extension.Dao;

public interface DeviceDao extends Dao<String, Device> {

	Long nextExamineTime(String id);
	
	List<DeviceInfo> list(DevicesParam param);
	
	DeviceDetail detail(String id);

	List<DeviceInspect> deviceInspectList(@Valid DeviceInspectParam param);
}
