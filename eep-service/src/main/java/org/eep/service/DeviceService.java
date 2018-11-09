package org.eep.service;

import java.util.List;
import java.util.Set;

import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.model.DeviceInfo;
import org.eep.common.bean.model.InspectDetail;
import org.eep.common.bean.model.InspectInfo;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.DevicesParam;
import org.eep.common.bean.param.InspectsParam;
import org.eep.manager.DeviceManager;
import org.rubik.bean.core.model.Pager;
import org.rubik.bean.core.param.Param;
import org.rubik.bean.core.param.SidParam;
import org.rubik.mybatis.PagerUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("device")
public class DeviceService {
	
	@javax.annotation.Resource
	private DeviceManager deviceManager;
	
	public void categoryCreate(CategoryParam param) {
		deviceManager.categoryCreate(param);
	}
	
	public void categoryModify(CategoryParam param) { 
		deviceManager.categoryModify(param);
	}
	
	public void categoryDelete(SidParam param) { 
		deviceManager.categoryDelete(param);
	}
	
	public void inspectCreate(String cid, String rid, String content, long committer, Set<String> devices, List<Resource> resources) { 
		deviceManager.inspectCreate(cid, rid, content, committer, devices, resources);
	}
	
	public void alertCheck() { 
		deviceManager.alertCheck();
	}
	
	public List<Device> devices(Set<String> ids) {
		return deviceManager.devices(ids);
	}
	
	public InspectDetail inspectDetail(long id) {
		return deviceManager.inspectDetail(id);
	}
	
	public Pager<DeviceCategory> categories(Param param) {
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(deviceManager.categories());
	}
	
	public Pager<DeviceInfo> devices(DevicesParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(deviceManager.devices(param));
	}
	
	public Pager<InspectInfo> inspects(InspectsParam param) { 
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(deviceManager.inspects(param));
	}
}
