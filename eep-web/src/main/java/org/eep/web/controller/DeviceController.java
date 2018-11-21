package org.eep.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.eep.bean.param.RepairCreateParam;
import org.eep.common.Codes;
import org.eep.common.Consts;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Device;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.ResourceType;
import org.eep.common.bean.model.RepairDetail;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.CategoryParam;
import org.eep.common.bean.param.CategoryQueryParam;
import org.eep.common.bean.param.DevicesParam;
import org.eep.common.bean.param.RepairsParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.service.CompanyService;
import org.eep.service.DeviceService;
import org.eep.service.RegionService;
import org.eep.util.RegionUtil;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.Constants;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.LidParam;
import org.rubik.bean.core.param.SidParam;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.KeyUtil;
import org.rubik.web.Uploader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("device")
public class DeviceController {
	
	@javax.annotation.Resource
	private Uploader uploader;
	@javax.annotation.Resource
	private RegionService regionService;
	@javax.annotation.Resource
	private DeviceService deviceService;
	@javax.annotation.Resource
	private CompanyService companyService;
	@javax.annotation.Resource
	private RubikConfigService rubikConfigService;
	
	@ResponseBody
	@RequestMapping("list")
	public Object devices(@RequestBody @Valid DevicesParam param) {
		if (null != param.getRegion()) {
			Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
			regionService.userRegionVerify(param.requestor(), param.getRegion());
			RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		}
		return deviceService.devices(param);
	}
	
	@ResponseBody
	@RequestMapping("list/company")
	public Object devicesCompany(@RequestBody @Valid DevicesParam param) {
		Assert.hasText(param.getCid(), Code.PARAM_ERR, "cid miss");
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.getUser().getCid().equals(param.getCid()), Code.FORBID);
		return deviceService.devices(param);
	}

	/**
	 * 维保记录列表(辖区)
	 */
	@ResponseBody
	@RequestMapping("repairs/area")
	public Object repairsArea(@RequestBody @Valid RepairsParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		regionService.userRegionVerify(param.requestor(), param.getRegion());
		RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return deviceService.repairs(param);
	}
	
	/**
	 * 维保记录列表(维保单位)
	 */
	@ResponseBody
	@RequestMapping("repairs/repair")
	public Object inspectsRepair(@RequestBody @Valid RepairsParam param) {
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.getCompany().getType() == CompanyType.REPAIR, Code.FORBID);
		param.setRid(visitor.getCompany().getId());
		return deviceService.repairs(param);
	}
	
	/**
	 * 维保记录详情页(辖区)
	 */
	@ResponseBody
	@RequestMapping("repair/detail")
	public Object repairDetail(@RequestBody @Valid LidParam param) {
		RepairDetail detail = deviceService.repairDetail(param.getId());
		if (null != detail) {		// 检测区域权限
			Company company = companyService.company(detail.getCid());
			regionService.userRegionVerify(param.requestor(), company.getRegion());
		}
		return detail;
	}
	
	/**
	 * 维保记录详情页(维保单位)
	 */
	@ResponseBody
	@RequestMapping("repair/detail/repair")
	public Object inspectDetailRepair(@RequestBody @Valid LidParam param) {
		Visitor visitor = param.requestor();
		Company company = visitor.getCompany();
		Assert.isTrue(company.getType() == CompanyType.REPAIR, Code.FORBID);
		RepairDetail detail = deviceService.repairDetail(param.getId());
		Assert.isTrue(detail.getRid().equals(company.getId()));
		return detail;
	}
	
	/**
	 * 添加维保记录
	 */
	@ResponseBody
	@RequestMapping("repair/create")
	public Object repairCreate(RepairCreateParam param) {
		Visitor visitor = param.requestor();
		// 上传用户必须属于维保单位
		Assert.isTrue(visitor.getCompany().getType() == CompanyType.REPAIR, Code.FORBID);
		int resourceMaximum = rubikConfigService.config(Constants.RESOURCE_MAXIMUM_REPAIR);
		Assert.isTrue(param.getFiles().size() <= resourceMaximum, Codes.RESOURCE_MAXIMUM);
		int deviceMaximum = rubikConfigService.config(Constants.DEVICE_MAXIMUM_REPAIR);
		Assert.isTrue(param.getDevices().size() <= deviceMaximum, Codes.DEVICE_MAXIMUM);
		Company company = Assert.notNull(companyService.company(param.getCid()), Codes.COMPANY_NOT_EXIST);
		// 上传设备所在单位必须是使用单位
		Assert.isTrue(company.getType() == CompanyType.USE, Code.FORBID);
		// 设备数目不可以超过指定的数量
		int maximum = rubikConfigService.config(Consts.DEVICE_REPAIR_MAXIMUM);
		Assert.isTrue(param.getDevices().size() <= maximum, Codes.REPAIR_DEVICE_MAXIMUM);
		List<Device> devices = deviceService.devices(param.getDevices());
		Assert.isTrue(devices.size() == param.getDevices().size(), Codes.DEVICE_NOT_EXIST);
		devices.forEach(device -> Assert.isTrue(device.getCid().equals(param.getCid()), Code.FORBID));
		String category = "device/repair";
		String directory = uploader.resourceDirectory();
		String url = rubikConfigService.config(Constants.RESOURCE_URL);
		List<Resource> resources = new ArrayList<Resource>();
		int priority = 0;
		for (MultipartFile file : param.getFiles()) {
			String name = KeyUtil.timebasedId();
			String suffix = uploader.save(file, directory, category, name);
			url = url.endsWith("\\/") ? url + suffix : url + "/" + suffix;
			String path = directory.endsWith("\\/") ? directory + suffix : directory + File.separator + suffix;
			Resource resource = EntityGenerator.newResource(file.getSize(), url, path, name, ResourceType.DEVICE_REPAIR, null, ++priority);
			resources.add(resource);
		}
		deviceService.repairCreate(param.getCid(), visitor.getCompany().getId(), param.getNextTime(),param.getContent(), visitor.id(), param.getDevices(), resources);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("categories")
	public Object categories(@RequestBody @Valid CategoryQueryParam param) { 
		return deviceService.categories(param);
	}
	
	@ResponseBody
	@RequestMapping("category/create")
	public Object categoryCreate(@RequestBody @Valid CategoryParam param) { 
		deviceService.categoryCreate(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("category/modify")
	public Object categoryModify(@RequestBody @Valid CategoryParam param) { 
		deviceService.categoryModify(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("category/delete")
	public Object categoryDelete(@RequestBody @Valid SidParam param) { 
		deviceService.categoryDelete(param);
		return Result.ok();
	}
}
