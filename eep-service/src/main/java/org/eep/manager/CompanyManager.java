package org.eep.manager;

import java.util.List;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Employee;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.OperatorCert;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.OperatorInfo;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.CompaniesParam;
import org.eep.common.bean.param.EmployeeCreateParam;
import org.eep.common.bean.param.IntrospectCreateParam;
import org.eep.common.bean.param.OperatorsParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.CompanyDao;
import org.eep.mybatis.dao.EmployeeDao;
import org.eep.mybatis.dao.IntrospectDao;
import org.eep.mybatis.dao.OperatorCertDao;
import org.eep.mybatis.dao.OperatorDao;
import org.eep.mybatis.dao.ResourceDao;
import org.eep.service.RegionService;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.exception.AssertException;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Query;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CompanyManager {

	@javax.annotation.Resource
	private CompanyDao companyDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@javax.annotation.Resource
	private EmployeeDao employeeDao;
	@javax.annotation.Resource
	private UserManager userManager;
	@javax.annotation.Resource
	private OperatorDao operatorDao;
	@javax.annotation.Resource
	private RegionService regionService;
	@javax.annotation.Resource
	private IntrospectDao introspectDao;
	@javax.annotation.Resource
	private OperatorCertDao operatorCertDao;
	
	public void visitorSetup(Visitor visitor, long employeeId) {
		Employee employee = Assert.notNull(employeeDao.selectByKey(employeeId), Codes.EMPLOYEE_NOT_EXIST);
		Assert.isTrue(employee.getUid() == visitor.id(), Code.FORBID);
		visitor.setEmployee(employee);
		visitor.setCompany(companyDao.selectByKey(employee.getCid()));
	}
	
	@Transactional
	public Employee employeeCreate(EmployeeCreateParam param) {
		User user = Assert.notNull(userManager.user(param.getUid()), Code.USER_NOT_EIXST);
		Company company = Assert.notNull(companyDao.selectByKey(param.getCid()), Codes.COMPANY_NOT_EXIST);
		regionService.userRegionVerify(param.requestor().id(), company.getRegion());
		Employee employee = EntityGenerator.newEmployee(user, company);
		try {
			employeeDao.insert(employee);
		} catch (DuplicateKeyException e) {
			throw AssertException.error(Codes.EMPLOYEE_DUPLICATED);
		}
		return employee;
	}
	
	public long introspectCreate(IntrospectCreateParam param) { 
		Introspect introspect = EntityGenerator.newIntrospect(param);
		introspectDao.insert(introspect);
		return introspect.getId();
	}
	
	@Transactional
	public void introspectUpload(Introspect introspect, List<Resource> resources) { 
		resources.forEach(resource -> resource.setOwner(String.valueOf(introspect.getId())));
		resourceDao.insertMany(resources);
	}
	
	public Company company(String id) {
		return companyDao.selectByKey(id);
	}
	
	public Introspect introspect(long id) {
		return introspectDao.selectByKey(id);
	}
	
	public List<OperatorCert> operatorCerts(Query query) {
		return operatorCertDao.queryList(query);
	}
	
	public List<CompanyInfo> companies(CompaniesParam param) { 
		return companyDao.list(param);
	}
	
	public List<OperatorInfo> operators(OperatorsParam param) { 
		return operatorDao.list(param);
	}
}
