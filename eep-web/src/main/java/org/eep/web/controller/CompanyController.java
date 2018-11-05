package org.eep.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.eep.bean.param.IntrospectUploadParam;
import org.eep.common.Codes;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.ResourceType;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.CompaniesParam;
import org.eep.common.bean.param.EmployeeCreateParam;
import org.eep.common.bean.param.IntrospectCreateParam;
import org.eep.common.bean.param.OperatorCertsParam;
import org.eep.common.bean.param.OperatorsParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.service.CompanyService;
import org.eep.service.RegionService;
import org.eep.util.RegionUtil;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.Constants;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Result;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.KeyUtil;
import org.rubik.web.Uploader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("company")
public class CompanyController {
	
	@javax.annotation.Resource
	private Uploader uploader;
	@javax.annotation.Resource
	private RegionService regionService;
	@javax.annotation.Resource
	private CompanyService companyService;
	@javax.annotation.Resource
	private RubikConfigService rubikConfigService;
	
	/**
	 * 使用单位列表所有
	 */
	@ResponseBody
	@RequestMapping("list/use")
	public Object uses(@RequestBody @Valid CompaniesParam param) {
		param.setType(CompanyType.USE);
		if (null != param.getRegion()) 
			RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return companyService.companies(param);
	}
	
	/**
	 * 维保单位列表所有
	 */
	@ResponseBody
	@RequestMapping("list/repair")
	public Object repairs(@RequestBody @Valid CompaniesParam param) {
		param.setType(CompanyType.REPAIR);
		if (null != param.getRegion()) 
			RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return companyService.companies(param);
	}
	
	/**
	 * 使用单位列表(地区)
	 */
	@ResponseBody
	@RequestMapping("list/use/area")
	public Object usesArea(@RequestBody @Valid CompaniesParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		SysRegion region = regionService.userRegionVerify(param.requestor().id(), param.getRegion());
		RegionUtil.setRange(param, region);
		param.setType(CompanyType.USE);
		return companyService.companies(param);
	}
	
	/**
	 * 维保单位列表(地区)
	 */
	@ResponseBody
	@RequestMapping("list/repair/area")
	public Object repairsArea(@RequestBody @Valid CompaniesParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		SysRegion region = regionService.userRegionVerify(param.requestor().id(), param.getRegion());
		RegionUtil.setRange(param, region);
		param.setType(CompanyType.REPAIR);
		return companyService.companies(param);
	}
	
	/**
	 * 添加雇员
	 */
	@ResponseBody
	@RequestMapping("employee/create")
	public Object employeeCreate(@RequestBody @Valid EmployeeCreateParam param) { 
		return companyService.employeeCreate(param);
	}
	
	/**
	 * 作业人员列表
	 */
	@ResponseBody
	@RequestMapping("operators")
	public Object operators(@RequestBody @Valid OperatorsParam param) { 
		return companyService.operators(param);
	}
	
	/**
	 * 作业人员列表
	 */
	@ResponseBody
	@RequestMapping("operators/employee")
	public Object operatorsEmployee(@RequestBody @Valid OperatorsParam param) { 
		Visitor visitor = param.requestor();
		Company company = visitor.getCompany();
		param.setCid(company.getId());
		return companyService.operators(param);
	}
	
	/**
	 * 作业人员资质列表
	 */
	@ResponseBody
	@RequestMapping("operator/certs")
	public Object operatorCerts(@RequestBody @Valid OperatorCertsParam param) {
		return companyService.operatorCerts(param);
	}
	
	/**
	 * 上传自查自纠表(使用单位)
	 */
	@ResponseBody
	@RequestMapping("introspect/create")
	public Object introspectCreate(@RequestBody @Valid IntrospectCreateParam param) { 
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.getCompany().getType() == CompanyType.USE, Code.FORBID);
		return companyService.introspectCreate(param);
	}
	
	/**
	 * 上传自查自纠文件资料
	 */
	@ResponseBody
	@RequestMapping("introspect/upload")
	public Object introspectUpload(IntrospectUploadParam param) {
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.getCompany().getType() == CompanyType.USE, Code.FORBID);
		int resourceMaximum = rubikConfigService.config(Constants.RESOURCE_MAXIMUM_INTROSPECT);
		Assert.isTrue(param.getFiles().size() <= resourceMaximum, Codes.RESOURCE_MAXIMUM);
		Introspect introspect = Assert.notNull(companyService.introspect(param.getId()), Codes.INTROSPECT_NOT_EXIST);
		Assert.isTrue(introspect.getCid().equals(visitor.getCompany().getId()), Code.FORBID);
		String category = "company/introspect";
		String directory = uploader.resourceDirectory();
		String url = rubikConfigService.config(Constants.RESOURCE_URL);
		List<Resource> resources = new ArrayList<Resource>();
		int priority = 0;
		for (MultipartFile file : param.getFiles()) {
			String name = KeyUtil.timebasedId();
			String suffix = uploader.save(file, directory, category, name);
			url = url.endsWith("\\/") ? url + suffix : url + "/" + suffix;
			String path = directory.endsWith("\\/") ? directory + suffix : directory + File.separator + suffix;
			Resource resource = EntityGenerator.newResource(file.getSize(), url, path, name, ResourceType.COMPANY_INTROSPECT, null, ++priority);
			resources.add(resource);
		}
		companyService.introspectUpload(introspect, resources);
		return Result.ok();
	}
}
