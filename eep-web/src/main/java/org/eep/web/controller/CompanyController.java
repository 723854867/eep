package org.eep.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.eep.bean.param.InspectCreateParam;
import org.eep.bean.param.IntrospectUploadParam;
import org.eep.chuanglan.ChuangLanApi;
import org.eep.chuanglan.model.VarSmsRequest;
import org.eep.common.Codes;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.RectifyNotice;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.ResourceType;
import org.eep.common.bean.model.InspectDetail;
import org.eep.common.bean.model.IntrospectDetail;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.AlertStatisticParam;
import org.eep.common.bean.param.AlertsParam;
import org.eep.common.bean.param.CompaniesParam;
import org.eep.common.bean.param.EmployeeCreateParam;
import org.eep.common.bean.param.InspectsParam;
import org.eep.common.bean.param.IntrospectCreateParam;
import org.eep.common.bean.param.IntrospectParam;
import org.eep.common.bean.param.OperatorCertsParam;
import org.eep.common.bean.param.OperatorsParam;
import org.eep.common.bean.param.RectifyNoticeCreateParam;
import org.eep.common.bean.param.RectifyNoticesParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.service.CommonService;
import org.eep.service.CompanyService;
import org.eep.service.RegionService;
import org.eep.util.RegionUtil;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.Constants;
import org.rubik.bean.core.enums.Locale;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.LidParam;
import org.rubik.bean.core.param.Param;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.soa.config.bean.entity.SysWord;
import org.rubik.util.common.KeyUtil;
import org.rubik.util.common.PhoneUtil;
import org.rubik.util.common.StringUtil;
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
	private ChuangLanApi chuangLanApi;
	@javax.annotation.Resource
	private CommonService commonService;
	@javax.annotation.Resource
	private RegionService regionService;
	@javax.annotation.Resource
	private CompanyService companyService;
	@javax.annotation.Resource
	private RubikConfigService rubikConfigService;
	
	@ResponseBody
	@RequestMapping("info")
	public Object info(@RequestBody @Valid Param param) {
		Visitor visitor = param.requestor();
		return visitor.getCompany();
	}
	
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
	 * 使用单位列表所有(不分页)
	 */
	@ResponseBody
	@RequestMapping("list/use/all")
	public Object uses_(@RequestBody @Valid CompaniesParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		SysRegion region = regionService.userRegionVerify(param.requestor(), param.getRegion());
		RegionUtil.setRange(param, region);
		param.setType(CompanyType.USE);
		return companyService.companiesTitle(param);
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
		SysRegion region = regionService.userRegionVerify(param.requestor(), param.getRegion());
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
		SysRegion region = regionService.userRegionVerify(param.requestor(), param.getRegion());
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
		companyService.employeeCreate(param);
		return Result.ok();
	}
	
	/**
	 * 删除雇员
	 */
	@ResponseBody
	@RequestMapping("employee/delete")
	public Object employeeDelete(@RequestBody @Valid LidParam param) { 
		companyService.employeeDelete(param);
		return Result.ok();
	}
	
	/**
	 * 作业人员列表
	 */
	@ResponseBody
	@RequestMapping("operator/list")
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
	 * 自查自纠列表
	 */
	@ResponseBody
	@RequestMapping("introspect/list")
	public Object introspects(@RequestBody @Valid IntrospectParam param) {
		return companyService.introspectList(param);
	}
	
	/**
	 * 自查自纠列表(使用单位)
	 */
	@ResponseBody
	@RequestMapping("introspect/list/use")
	public Object introspectsUse(@RequestBody @Valid IntrospectParam param) {
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.getCompany().getType() == CompanyType.USE, Code.FORBID);
		param.setCid(visitor.getCompany().getId());
		return companyService.introspectList(param);
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
	 * 自查自纠详情
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("introspect/detail")
	public Object introspectDetail(@RequestBody @Valid LidParam param) {
		Introspect introspect = Assert.notNull(companyService.introspect(param.getId()), Codes.INTROSPECT_NOT_EXIST);
		IntrospectDetail detail = new IntrospectDetail(introspect);
		Query query = new Query().and(Criteria.eq("type", ResourceType.COMPANY_INTROSPECT), Criteria.eq("owner", param.getId()));
		detail.setResources(commonService.resources(query).getList());
		return detail;
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
	
	/**
	 * 整改列表(辖区)
	 */
	@ResponseBody
	@RequestMapping("rectify/notice/list/area")
	public Object rectifyNoticesArea(@RequestBody @Valid RectifyNoticesParam param) { 
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		regionService.userRegionVerify(param.requestor(), param.getRegion());
		RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return companyService.rectifyNotices(param);
	}
	
	/**
	 * 整改列表(使用单位)
	 */
	@ResponseBody
	@RequestMapping("rectify/notice/list/use")
	public Object rectifyNoticesUse(@RequestBody @Valid RectifyNoticesParam param) { 
		Visitor visitor = param.requestor();
		Company company = visitor.getCompany();
		param.setCname(null);
		param.setCid(company.getId());
		return companyService.rectifyNotices(param);
	}
	
	/**
	 * 添加整改通知
	 */
	@ResponseBody
	@RequestMapping("rectify/notice/create")
	public Object rectifyNoticeCreate(@RequestBody @Valid RectifyNoticeCreateParam param) { 
		Company company = Assert.notNull(companyService.company(param.getCid()), Codes.COMPANY_NOT_EXIST);
		Assert.isTrue(company.getType() == CompanyType.USE, Code.FORBID);
		regionService.userRegionVerify(param.requestor(), company.getRegion());
		RectifyNotice notice = companyService.rectifyNoticeCreate(param);
		if (param.isSmsSend() && StringUtil.hasText(company.getContactsPhone()) && PhoneUtil.isMobile(company.getContactsPhone())) {
			VarSmsRequest request = new VarSmsRequest();
			SysWord word = rubikConfigService.word("rectify_word_sms", Locale.ZH_CN);
			request.msg(word.getValue());
			request.addReceiver(String.valueOf(PhoneUtil.getNationalNumber(company.getContactsPhone())), company.getName());
		}
		return notice;
	}
	
	/**
	 * 完成整改
	 */
	@ResponseBody
	@RequestMapping("rectify/notice/finish")
	public Object rectifyNoticeFinish(@RequestBody @Valid LidParam param) { 
		companyService.rectifyNoticeFinish(param);
		return Result.ok();
	}
	
	/**
	 * 首页统计(辖区)
	 */
	@ResponseBody
	@RequestMapping("alert/statistic/area")
	public Object alertStatisticArea(@RequestBody @Valid AlertStatisticParam param) { 
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		regionService.userRegionVerify(param.requestor(), param.getRegion());
		RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return companyService.alertStatistic(param);
	}
	
	/**
	 * 首页统计(使用单位)
	 */
	@ResponseBody
	@RequestMapping("alert/statistic/use")
	public Object alertStatisticUse(@RequestBody @Valid AlertStatisticParam param) { 
		Visitor visitor = param.requestor();
		Company company = visitor.getCompany();
		Assert.isTrue(company.getType() == CompanyType.USE, Code.FORBID);
		param.setCid(company.getId());
		return companyService.alertStatistic(param);
	}
	
	/**
	 * 检查记录列表(辖区)
	 */
	@ResponseBody
	@RequestMapping("inspects/area")
	public Object inspectsArea(@RequestBody @Valid InspectsParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		regionService.userRegionVerify(param.requestor(), param.getRegion());
		RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return companyService.inspects(param);
	}
	
	/**
	 * 检查记录(单位)
	 */
	@ResponseBody
	@RequestMapping("inspects/use")
	public Object inspectsUse(@RequestBody @Valid InspectsParam param) {
		Visitor visitor = param.requestor();
		param.setCid(visitor.getCompany().getId());
		return companyService.inspects(param);
	}
	
	/**
	 * 检查记录详情(辖区)
	 */
	@ResponseBody
	@RequestMapping("inspect/detail")
	public Object inspectDetailArea(@RequestBody @Valid LidParam param) {
		InspectDetail detail = Assert.notNull(companyService.inspectDetail(param.getId()), Codes.INSPECT_NOT_EXIST);
		Company company = companyService.company(detail.getCid());
		regionService.userRegionVerify(param.requestor(), company.getRegion());
		return detail;
	}
	
	/**
	 * 检查记录详情(使用单位)
	 */
	@ResponseBody
	@RequestMapping("inspect/detail/use")
	public Object inspectDetailUse(@RequestBody @Valid LidParam param) {
		Visitor visitor = param.requestor();
		Company company = visitor.getCompany();
		InspectDetail detail = Assert.notNull(companyService.inspectDetail(param.getId()), Codes.INSPECT_NOT_EXIST);
		Assert.isTrue(detail.getCid().equals(company.getId()));
		return detail;
	}
	
	/**
	 * 创建检查记录
	 */
	@ResponseBody
	@RequestMapping("inspect/create")
	public Object inspectCreate(InspectCreateParam param) { 
		Company company = Assert.notNull(companyService.company(param.getCid()), Codes.COMPANY_NOT_EXIST);
		regionService.userRegionVerify(param.requestor(), company.getRegion());
		int resourceMaximum = rubikConfigService.config(Constants.RESOURCE_MAXIMUM_INSPECT);
		Assert.isTrue(null == param.getFiles() || param.getFiles().size() <= resourceMaximum, Codes.RESOURCE_MAXIMUM);
		List<Resource> resources = new ArrayList<Resource>();
		if(null != param.getFiles()) {
			String category = "company/inspect";
			String directory = uploader.resourceDirectory();
			String resourceUrl = rubikConfigService.config(Constants.RESOURCE_URL);
			int priority = 0;
			for (MultipartFile file : param.getFiles()) {
				String name = KeyUtil.timebasedId();
				String suffix = uploader.save(file, directory, category, name);
				String url = resourceUrl.endsWith("\\/") ? resourceUrl + suffix : resourceUrl + "/" + suffix;
				String path = directory.endsWith("\\/") ? directory + suffix : directory + File.separator + suffix;
				Resource resource = EntityGenerator.newResource(file.getSize(), url, path, name, ResourceType.COMPANY_INSPECT, null, ++priority);
				resources.add(resource);
			}
		}
		Inspect inspect = companyService.inspectCreate(param.getCid(), param.getTime(), param.getContent(), param.requestor().id(), resources);
		if (param.isSmsSend() && StringUtil.hasText(company.getContactsPhone()) && PhoneUtil.isMobile(company.getContactsPhone())) {
			VarSmsRequest request = new VarSmsRequest();
			SysWord word = rubikConfigService.word("rectify_word_inspect", Locale.ZH_CN);
			request.msg(word.getValue());
			request.addReceiver(String.valueOf(PhoneUtil.getNationalNumber(company.getContactsPhone())), company.getName());
			chuangLanApi.sendSms(request);
		}
		return inspect;
	}
	
	/**
	 * 警告列表(辖区)
	 */
	@ResponseBody
	@RequestMapping("alerts/area")
	public Object alertsArea(@RequestBody @Valid AlertsParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		regionService.userRegionVerify(param.requestor(), param.getRegion());
		RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return companyService.alerts(param);
	}
	
	/**
	 * 警告列表(使用单位)
	 */
	@ResponseBody
	@RequestMapping("alerts/use")
	public Object alertsUse(@RequestBody @Valid AlertsParam param) {
		Visitor visitor = param.requestor();
		Company company = visitor.getCompany();
		Assert.isTrue(company.getType() == CompanyType.USE, Code.FORBID);
		param.setCid(company.getId());
		return companyService.alerts(param);
	}
}
