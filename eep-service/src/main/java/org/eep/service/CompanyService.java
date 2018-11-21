package org.eep.service;

import java.util.List;

import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.OperatorCert;
import org.eep.common.bean.entity.RectifyNotice;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.model.AlertInfo;
import org.eep.common.bean.model.AlertStatistic;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.CompanyTitle;
import org.eep.common.bean.model.InspectDetail;
import org.eep.common.bean.model.InspectInfo;
import org.eep.common.bean.model.IntrospectInfo;
import org.eep.common.bean.model.OperatorInfo;
import org.eep.common.bean.model.RectifyNoticeInfo;
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
import org.eep.manager.CompanyManager;
import org.rubik.bean.core.model.Pager;
import org.rubik.bean.core.param.LidParam;
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
	
	public void employeeCreate(EmployeeCreateParam param) { 
		companyManager.employeeCreate(param);
	}
	
	public void employeeDelete(LidParam param) { 
		companyManager.employeeDelete(param);
	}
	
	public long introspectCreate(IntrospectCreateParam param) { 
		return companyManager.introspectCreate(param);
	}
	
	public void introspectUpload(Introspect introspect, List<Resource> resources) { 
		companyManager.introspectUpload(introspect, resources);
	}
	
	public RectifyNotice rectifyNoticeCreate(RectifyNoticeCreateParam param) {
		return companyManager.rectifyNoticeCreate(param);
	}
	
	public void rectifyNoticeFinish(LidParam param) { 
		companyManager.rectifyNoticeFinish(param);
	}
	
	public Inspect inspectCreate(String cid, long time, String content, long committer, List<Resource> resources) { 
		return companyManager.inspectCreate(cid, time, content, committer, resources);
	}
	
	public void alertCheck() { 
		companyManager.alertCheck();
	}
	
	public Company company(String id) {
		return companyManager.company(id);
	}
	
	public Introspect introspect(long id) {
		return companyManager.introspect(id);
	}
	
	public InspectDetail inspectDetail(long id) {
		return companyManager.inspectDetail(id);
	}
	
	public Pager<InspectInfo> inspects(InspectsParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.inspects(param));
	}
	
	public Pager<CompanyInfo> companies(CompaniesParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.companies(param));
	}
	
	public List<CompanyTitle> companies() {
		return companyManager.companies();
	}
	
	public Pager<AlertInfo> alerts(AlertsParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.alerts(param));
	}
	
	public Pager<OperatorInfo> operators(OperatorsParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.operators(param));
	}
	
	public Pager<OperatorCert> operatorCerts(OperatorCertsParam param) { 
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.operatorCerts(param.getQuery()));
	}

	public Pager<IntrospectInfo> introspectList(IntrospectParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.introspects(param));
	}
	
	public List<AlertStatistic> alertStatistic(AlertStatisticParam param) {
		return companyManager.alertStatistic(param);
	}
	
	public Pager<RectifyNoticeInfo> rectifyNotices(RectifyNoticesParam param) { 
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(companyManager.rectifyNotices(param));
	}
}
