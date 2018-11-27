package org.eep.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Alert;
import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.Repair;
import org.eep.common.bean.entity.RepairDevice;
import org.eep.common.bean.entity.LogExamine;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.WarnLevel;
import org.eep.common.bean.model.DeviceInfo;
import org.eep.common.bean.model.RepairDetail;
import org.eep.common.bean.model.RepairInfo;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.DevicesParam;
import org.eep.common.bean.param.RepairsParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.AlertDao;
import org.eep.mybatis.dao.DeviceCategoryDao;
import org.eep.mybatis.dao.DeviceDao;
import org.eep.mybatis.dao.RepairDao;
import org.eep.mybatis.dao.RepairDeviceDao;
import org.eep.mybatis.dao.LogExamineDao;
import org.eep.mybatis.dao.ResourceDao;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.Constants;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.MultiListMap;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.SidParam;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.CollectionUtil;
import org.rubik.util.common.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceManager {
	
	@javax.annotation.Resource
	private AlertDao alertDao;
	@javax.annotation.Resource
	private DeviceDao deviceDao;
	@javax.annotation.Resource
	private RepairDao repairDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private LogExamineDao logExamineDao;
	@javax.annotation.Resource
	private CompanyManager companyManager;
	@javax.annotation.Resource
	private RepairDeviceDao repairDeviceDao;
	@javax.annotation.Resource
	private DeviceCategoryDao deviceCategoryDao;
	@javax.annotation.Resource
	private RubikConfigService rubikConfigService;
	
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
	public void repairCreate(String cid, String rid, int nextTime, String content, long committer, Set<String> devices, List<Resource> resources) { 
		Repair repair = EntityGenerator.newRepair(cid, rid,nextTime, content, committer);
		repairDao.insert(repair);
		List<RepairDevice> list = new ArrayList<RepairDevice>();
		devices.forEach(deviceId -> list.add(EntityGenerator.newRepairDevice(repair.getId(), deviceId)));
		repairDeviceDao.insertMany(list);
		resources.forEach(resource -> resource.setOwner(String.valueOf(repair.getId())));
		resourceDao.insertMany(resources);
	}
	
	@Transactional
	public void alertCheck() { 
		log.info("开始更新设备状态...");
		Query query = new Query().and(Criteria.in("type", AlertType.EXAMINE_DATE_NILL, AlertType.EXAMINE_DATE_EXPIRE_LIGHT, AlertType.EXAMINE_DATE_EXPIRE_SERIOUS));
		alertDao.deleteByQuery(query);
		List<Device> devices = deviceDao.selectList();
		if (!CollectionUtil.isEmpty(devices)) {
			List<Alert> alerts = new ArrayList<Alert>();
			int examineThreshold = rubikConfigService.config(Constants.EXAMINE_THRESHOLD_DAY);
			MultiListMap<String, LogExamine> examines = new MultiListMap<String, LogExamine>();
			logExamineDao.selectList().forEach(examine -> examines.add(examine.getDeviceId(), examine));
			for (Device device : devices) {
				// 判断检测日期
				List<LogExamine> l = examines.get(device.getId());
				if (CollectionUtil.isEmpty(l))			// 如果获取不到检测记录当红灯处理
					alerts.add(EntityGenerator.newAlert(device.getCid(), AlertType.EXAMINE_DATE_NILL, WarnLevel.RED, device.getId()));
				else {										// 获取最近一条检测记录的检测日期
					l.sort((o1, o2) -> {
						if (o1.getNextTime() > o2.getNextTime())
							return -1;
						else if (o1.getNextTime() < o2.getNextTime())
							return 1;
						return 0;
					});
					LogExamine recently = l.get(0);
					long now = DateUtil.current();
					if (now >= recently.getNextTime())			// 当前日期大于等于下次检测日期
						alerts.add(EntityGenerator.newAlert(device.getCid(), AlertType.EXAMINE_DATE_EXPIRE_SERIOUS, WarnLevel.RED, device.getId()));
					else {
						long time = recently.getNextTime() - examineThreshold * DateUtil.DAY_SECONDS;
						if (now >= time)								// 当前日期大于等于下次检测日期-30天
							alerts.add(EntityGenerator.newAlert(device.getCid(), AlertType.EXAMINE_DATE_EXPIRE_LIGHT, WarnLevel.YELLOW, device.getId()));
					}
				}
			}
			if (!CollectionUtil.isEmpty(alerts))
				CollectionUtil.dispersed(alerts, l -> alertDao.insertMany(l), 1000);
		}
		log.info("设备状态更新结束！");
	}
	
	public Long nextExamineTime(String id) {
		return deviceDao.nextExamineTime(id);
	}
	
	public List<DeviceCategory> categories(Query query) {
		return deviceCategoryDao.queryList(query);
	}
	
	public List<Device> devices(Set<String> ids) {
		return deviceDao.queryList(new Query().and(Criteria.in("id", ids)));
	}
	
	public RepairDetail repairDetail(long id) {
		return repairDao.detail(id);
	}
	
	public List<DeviceInfo> devices(DevicesParam param) {
		return deviceDao.list(param);
	}
	
	public List<RepairInfo> repairs(RepairsParam param) {
		return repairDao.list(param);
	}
}
