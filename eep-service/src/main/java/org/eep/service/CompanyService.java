package org.eep.service;

import java.util.List;

import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Employee;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.OperatorCert;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.OperatorInfo;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.CompaniesParam;
import org.eep.common.bean.param.EmployeeCreateParam;
import org.eep.common.bean.param.IntrospectCreateParam;
import org.eep.common.bean.param.OperatorCertsParam;
import org.eep.common.bean.param.OperatorsParam;
import org.eep.manager.CompanyManager;
import org.rubik.bean.core.model.Pager;
import org.rubik.mybatis.PagerUtil;
import org.rubik.soa.config.api.RubikConfigService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class CompanyService {

	@javax.annotation.Resource
	private RegionService regionService;
	@javax.annotation.Resource
	private CompanyManager companyManager;
	@javax.annotation.Resource
	private RubikConfigService rubikConfigService;
	
	public void visitorSetup(Visitor visitor, long employeeId) {
		companyManager.visitorSetup(visitor, employeeId);
	}
	
	public Employee employeeCreate(EmployeeCreateParam param) { 
		return companyManager.employeeCreate(param);
	}
	
	public long introspectCreate(IntrospectCreateParam param) { 
		return companyManager.introspectCreate(param);
	}
	
	public void introspectUpload(Introspect introspect, List<Resource> resources) { 
		companyManager.introspectUpload(introspect, resources);
	}
	
	public Company company(String id) {
		return companyManager.company(id);
	}
	
	public Introspect introspect(long id) {
		return companyManager.introspect(id);
	}
	
	public Pager<CompanyInfo> companies(CompaniesParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.companies(param));
	}
	
	public Pager<OperatorInfo> operators(OperatorsParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.operators(param));
	}
	
	public Pager<OperatorCert> operatorCerts(OperatorCertsParam param) { 
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.operatorCerts(param.getQuery()));
	}
}
