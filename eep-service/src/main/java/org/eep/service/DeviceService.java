package org.eep.service;

import java.util.List;
import java.util.Set;

import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.model.DeviceDetail;
import org.eep.common.bean.model.DeviceInfo;
import org.eep.common.bean.model.RepairDetail;
import org.eep.common.bean.model.RepairInfo;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.CategoryQueryParam;
import org.eep.common.bean.param.DevicesParam;
import org.eep.common.bean.param.RepairsParam;
import org.eep.manager.DeviceManager;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Pager;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.SidParam;
import org.rubik.mybatis.PagerUtil;
import org.rubik.util.common.DateUtil;
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
	
	public void repairCreate(String cid, String rid, int nextTime,String content, long committer, Set<String> devices, List<Resource> resources) { 
		deviceManager.repairCreate(cid, rid, nextTime,content, committer, devices, resources);
	}
	
	public void alertCheck() { 
		deviceManager.alertCheck();
	}
	
	public Long nextExamineTime(String id) {
		return deviceManager.nextExamineTime(id);
	}
	
	public List<Device> devices(Set<String> ids) {
		return deviceManager.devices(ids);
	}
	
	public RepairDetail repairDetail(long id) {
		return deviceManager.repairDetail(id);
	}
	
	public Pager<DeviceCategory> categories(CategoryQueryParam param) {
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		Query query = new Query();
		if(null != param.getCode())
			query.and(Criteria.like("code", param.getCode()));
		if(null != param.getName())
			query.and(Criteria.like("name", param.getName()));
		return PagerUtil.page(deviceManager.categories(query));
	}
	
	public Pager<DeviceInfo> devices(DevicesParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(deviceManager.devices(param));
	}
	
	public Pager<RepairInfo> repairs(RepairsParam param) { 
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(deviceManager.repairs(param));
	}

	public DeviceDetail detail(String id) {
		 DeviceDetail device = deviceManager.detail(id);
		 if(device.getNextTime()!=null)
			 device.setTime(DateUtil.getDate(device.getNextTime()*1000, DateUtil.YMD));
		 return device;
	}
}
