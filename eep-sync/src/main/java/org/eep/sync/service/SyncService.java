package org.eep.sync.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.DeviceCategory;
import org.eep.common.bean.entity.LogExamine;
import org.eep.common.bean.entity.Operator;
import org.eep.common.bean.entity.OperatorCert;
import org.eep.common.bean.entity.SysRegion;
import org.eep.manager.DeviceManager;
import org.eep.mybatis.dao.CompanyDao;
import org.eep.mybatis.dao.DeviceCategoryDao;
import org.eep.mybatis.dao.DeviceDao;
import org.eep.mybatis.dao.LogExamineDao;
import org.eep.mybatis.dao.OperatorCertDao;
import org.eep.mybatis.dao.OperatorDao;
import org.eep.mybatis.dao.SysRegionDao;
import org.eep.sync.bean.entity.ViewTsEquinspect;
import org.eep.sync.bean.entity.ViewTsEquipment;
import org.eep.sync.bean.entity.ViewTsOrganization;
import org.eep.sync.bean.entity.ViewTsPeroperator;
import org.eep.sync.bean.entity.ViewTsPeroperatorcert;
import org.eep.sync.mybatis.EntityGenerator;
import org.eep.sync.mybatis.dao.ViewTsCodeEqusortcodeDao;
import org.eep.sync.mybatis.dao.ViewTsEquinspectDao;
import org.eep.sync.mybatis.dao.ViewTsEquipmentDao;
import org.eep.sync.mybatis.dao.ViewTsOrganizationDao;
import org.eep.sync.mybatis.dao.ViewTsPeroperatorDao;
import org.eep.sync.mybatis.dao.ViewTsPeroperatorcertDao;
import org.rubik.bean.core.Assert;
import org.rubik.redis.Locker;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.CollectionUtil;
import org.rubik.util.common.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("syncService")
public class SyncService{
	
	private static final String DEVICE_ALERT_CHECK_KEY			= "string:device:alert:check";
	
	@Resource
	private Locker locker;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private CompanyDao companyDao;
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private SysRegionDao sysRegionDao;
	@Resource
	private LogExamineDao logExamineDao;
	@Resource
	private DeviceManager deviceManager;
	@Resource
	private OperatorCertDao operatorCertDao;
	@Resource
	private DeviceCategoryDao deviceCategoryDao;
	@Resource
	private ViewTsEquipmentDao viewTsEquipmentDao;
	@Resource
	private RubikConfigService rubikConfigService;
	@Resource
	private ViewTsEquinspectDao viewTsEquinspectDao;
	@Resource
	private ViewTsPeroperatorDao viewTsPeroperatorDao;
	@Resource
	private ViewTsOrganizationDao viewTsOrganizationDao;
	@Resource
	private ViewTsCodeEqusortcodeDao viewTsCodeEqusortcodeDao;
	@Resource
	private ViewTsPeroperatorcertDao viewTsPeroperatorcertDao;

	private Map<String, Device> devices = new HashMap<String, Device>();
	private Map<String, Company> companies = new HashMap<String, Company>();
	private Map<String, Operator> operators = new HashMap<String, Operator>();
	private Map<String, DeviceCategory> categories = new HashMap<String, DeviceCategory>();

	@Transactional
	public void sync() {
		String lockId = locker.tryLock(DEVICE_ALERT_CHECK_KEY, 3600000);
		Assert.hasText(lockId, Codes.DEVICE_ALERT_TASK_RUNNING);
		try {
			log.info("开始数据同步！");
			deviceDao.deleteAll();
			companyDao.deleteAll();
			operatorDao.deleteAll();
			logExamineDao.deleteAll();
			operatorCertDao.deleteAll();
			_syncCompany();
			_syncDeviceCategory();
			_syncDevice();
			_syncOperator();
			_syncExamine();
			_syncOperatorCert();
			log.info("数据同步完成！");
		} finally {
			locker.releaseLock(DEVICE_ALERT_CHECK_KEY, lockId);
		}
	}
	
	private void _syncCompany() { 
		log.info("开始同步单位数据...");
		Map<String, SysRegion> regions = new HashMap<String, SysRegion>();
		sysRegionDao.selectAll().values().forEach(region -> regions.put(region.getCode(), region));
		List<ViewTsOrganization> list = viewTsOrganizationDao.selectList();
		list.forEach(organization -> {
			if (organization.getOrgtype() == 1 || organization.getOrgtype() == 3) {
				Company company = EntityGenerator.newCompany(organization);
				if (!StringUtil.hasText(organization.getAreacode()))
					organization.setAreacode("3303000000");
				if(organization.getAreacode().equals("330327"))
					organization.setAreacode("3303270000");
				if (organization.getAreacode().equals("33032701"))
					organization.setAreacode("3303270100");
				if (organization.getAreacode().equals("33032708"))
					organization.setAreacode("3303270800");
				if (organization.getAreacode().equals("33032713"))
					organization.setAreacode("3303271300");
				if (organization.getAreacode().equals("33032715"))
					organization.setAreacode("3303271500");
				if (organization.getAreacode().equals("33032716"))
					organization.setAreacode("3303271600");
				if (organization.getAreacode().equals("33032722"))
					organization.setAreacode("3303272200");
				if (organization.getAreacode().equals("33032723"))
					organization.setAreacode("3303272300");
				if (organization.getAreacode().equals("33032724"))
					organization.setAreacode("3303272400");
				if (organization.getAreacode().equals("33032727"))
					organization.setAreacode("3303272700");
				if (organization.getAreacode().equals("33032740"))
					organization.setAreacode("3303274000");
				if (organization.getAreacode().equals("3303030300"))
					organization.setAreacode("3303031100");
				SysRegion region = regions.get(organization.getAreacode());
				if (null == region)
					throw new RuntimeException("未识别的行政区划代码");
				company.setRegion(region.getId());
				companies.put(company.getId(), company);
			} else 
				log.warn("单位 - {} 类型 - {} 不识别，不迁移该单位！", organization.getSid(), organization.getOrgtype());
		});
		CollectionUtil.dispersed(new ArrayList<Company>(companies.values()), l -> companyDao.insertMany(l), 1000);
		log.info("成功同步 {} 条单位数据！", companies.size());
	}
	
	private void _syncDeviceCategory() { 
		log.info("开始同步设备类型数据...");
		viewTsCodeEqusortcodeDao.selectAll().values().forEach(code -> {
			DeviceCategory category = EntityGenerator.newDeviceCategory(code);
			categories.put(category.getCode(), category);
		});
		CollectionUtil.dispersed(new ArrayList<DeviceCategory>(categories.values()), l -> deviceCategoryDao.insertMany(l), 1000);
		log.info("成功同步 {} 条设备类型数据！", categories.size());
	}
	
	private void _syncDevice() {
		log.info("开始同步设备数据...");
		List<ViewTsEquipment> list = viewTsEquipmentDao.selectList();
		list.forEach(equip -> {
			Company company = companies.get(equip.getSidBaseorgmain());
			if (null != company) {
				DeviceCategory category = categories.get(equip.getEqusortcode());
				if (null == category) 
					log.warn("设备 {} 类型码 {} 无法识别， 不迁移该条数据！", equip.getSid(), equip.getEqusortcode());
				else {
					Device device = EntityGenerator.newDevice(equip);
					device.setCode(category.getCode());
					devices.put(device.getId(), device);
				}
			} else 
				log.warn("设备 {} 对应使用单位 - {}不存在，不迁移该条数据！", equip.getSid(), equip.getSidBaseorgmain());
		});
		CollectionUtil.dispersed(new ArrayList<Device>(devices.values()), l -> deviceDao.insertMany(l), 1000);
		log.info("成功同步 {} 条设备数据！", devices.size());
	}
	
	private void _syncOperator() {
		log.info("开始同步作业人员数据...");
		List<ViewTsPeroperator> list = viewTsPeroperatorDao.selectList();
		list.forEach(temp -> {
			Company company = companies.get(temp.getSidBaseorgmain());
			if (null != company) {
				Operator operator = EntityGenerator.newOperator(temp);
				operators.put(operator.getId(), operator);
			} else
				log.warn("作业人员 {} 对应使用单位 - {}不存在，不迁移该条数据！", temp.getSid(), temp.getSidBaseorgmain());
		});
		CollectionUtil.dispersed(new ArrayList<Operator>(operators.values()), l -> operatorDao.insertMany(l), 1000);
		log.info("成功同步 {} 条作业人员数据！", companies.size());
	}
	
	private void _syncExamine() {
		log.info("开始同步设备检验日志数据...");
		Map<String, LogExamine> map = new HashMap<String, LogExamine>();
		List<ViewTsEquinspect> list = viewTsEquinspectDao.selectList();
		list.forEach(temp -> {
			Device device = devices.get(temp.getSidBaseequmain());
			if (null != device) {
				LogExamine examine = EntityGenerator.newLogExamine(temp);
				map.put(examine.getId(), examine);
			} else
				log.warn("设备检验日志 {} 对应设备 - {}不存在，不迁移该条数据！", temp.getSid(), temp.getSidBaseequmain());
		});
		CollectionUtil.dispersed(map.values(), l -> logExamineDao.insertMany(l), 1000);
		log.info("成功同步 {} 条设备检验数据！", companies.size());
	}
	
	private void _syncOperatorCert()  {
		log.info("开始同步作业人员资质数据...");
		Map<String, OperatorCert> map = new HashMap<String, OperatorCert>();
		List<ViewTsPeroperatorcert> list = viewTsPeroperatorcertDao.selectList();
		list.forEach(temp -> {
			Operator operator = operators.get(temp.getSidBaseperoperator());
			if (null != operator) {
				OperatorCert cert = EntityGenerator.newOperatorCert(temp, operator);
				map.put(cert.getId(), cert);
			} else
				log.warn("作业人员资质 {} 对应作业人员 - {}不存在，不迁移该条数据！", temp.getSid(), temp.getSidBaseperoperator());
		});
		CollectionUtil.dispersed(map.values(), l -> operatorCertDao.insertMany(l), 1000);
		log.info("成功同步 {} 条作业人员资质数据！", companies.size());
	}
}
