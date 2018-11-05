package org.eep.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.entity.InspectDevice;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.model.DeviceInfo;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.DevicesParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.DeviceCategoryDao;
import org.eep.mybatis.dao.DeviceDao;
import org.eep.mybatis.dao.InspectDao;
import org.eep.mybatis.dao.InspectDeviceDao;
import org.eep.mybatis.dao.ResourceDao;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.SidParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeviceManager {

	@javax.annotation.Resource
	private DeviceDao deviceDao;
	@javax.annotation.Resource
	private InspectDao inspectDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private InspectDeviceDao inspectDeviceDao;
	@javax.annotation.Resource
	private DeviceCategoryDao deviceCategoryDao;
	
	public void categoryCreate(CategoryParam param) { 
		deviceCategoryDao.insert(EntityGenerator.newDeviceCategory(param));
	}
	
	public void categoryModify(CategoryParam param) { 
		DeviceCategory category = Assert.notNull(deviceCategoryDao.selectByKey(param.getCode()), Codes.DEVICE_CATEGORY_NOT_EXIST);
		category.setName(param.getName());
		deviceCategoryDao.update(category);
	}
	
	public void categoryDelete(SidParam param) { 
		Assert.isTrue(1 == deviceCategoryDao.deleteByKey(param.getId()), Codes.DEVICE_CATEGORY_NOT_EXIST);
	}
	
	@Transactional
	public void repairCreate(String cid, String rid, String content, long committer, Set<String> devices, List<Resource> resources) { 
		Inspect inspect = EntityGenerator.newInspect(cid, rid, content, committer);
		inspectDao.insert(inspect);
		List<InspectDevice> list = new ArrayList<InspectDevice>();
		devices.forEach(deviceId -> list.add(EntityGenerator.newInspectDevice(inspect.getId(), deviceId)));
		inspectDeviceDao.insertMany(list);
		resources.forEach(resource -> resource.setOwner(String.valueOf(inspect.getId())));
		resourceDao.insertMany(resources);
	}
	
	public List<Device> devices(Set<String> ids) {
		return deviceDao.queryList(new Query().and(Criteria.in("id", ids)));
	}
	
	public List<DeviceInfo> devices(DevicesParam param) {
		return deviceDao.list(param);
	}
	
	public List<DeviceCategory> categories() {
		return deviceCategoryDao.selectList();
	}
}
