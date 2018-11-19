package org.eep.manager;

import java.util.ArrayList;
import java.util.List;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Alert;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.entity.OperatorCert;
import org.eep.common.bean.entity.RectifyNotice;
import org.eep.common.bean.entity.Resource;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.AuditType;
import org.eep.common.bean.enums.RectifyState;
import org.eep.common.bean.enums.WarnLevel;
import org.eep.common.bean.model.AlertStatistic;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.InspectDetail;
import org.eep.common.bean.model.InspectInfo;
import org.eep.common.bean.model.IntrospectInfo;
import org.eep.common.bean.model.OperatorInfo;
import org.eep.common.bean.model.RectifyNoticeInfo;
import org.eep.common.bean.param.AlertStatisticParam;
import org.eep.common.bean.param.CompaniesParam;
import org.eep.common.bean.param.EmployeeCreateParam;
import org.eep.common.bean.param.InspectsParam;
import org.eep.common.bean.param.IntrospectCreateParam;
import org.eep.common.bean.param.IntrospectParam;
import org.eep.common.bean.param.OperatorsParam;
import org.eep.common.bean.param.RectifyNoticeCreateParam;
import org.eep.common.bean.param.RectifyNoticesParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.AlertDao;
import org.eep.mybatis.dao.CompanyDao;
import org.eep.mybatis.dao.InspectDao;
import org.eep.mybatis.dao.IntrospectDao;
import org.eep.mybatis.dao.OperatorCertDao;
import org.eep.mybatis.dao.OperatorDao;
import org.eep.mybatis.dao.RectifyNoticeDao;
import org.eep.mybatis.dao.ResourceDao;
import org.eep.service.RegionService;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.Constants;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.MultiListMap;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.LidParam;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.CollectionUtil;
import org.rubik.util.common.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CompanyManager {

	@javax.annotation.Resource
	private AlertDao alertDao;
	@javax.annotation.Resource
	private InspectDao inspectDao;
	@javax.annotation.Resource
	private CompanyDao companyDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
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
	@javax.annotation.Resource
	private RectifyNoticeDao rectifyNoticeDao;
	@javax.annotation.Resource
	private RubikConfigService rubikConfigService;
	
	@Transactional
	public void employeeCreate(EmployeeCreateParam param) {
		User user = Assert.notNull(userManager.user(param.getUid()), Code.USER_NOT_EIXST);
		Company company = Assert.notNull(companyDao.selectByKey(param.getCid()), Codes.COMPANY_NOT_EXIST);
		regionService.userRegionVerify(param.requestor(), company.getRegion());
		user.setCid(company.getId());
		user.setUpdated(DateUtil.current());
		userManager.update(user);
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
	
	public RectifyNotice rectifyNoticeCreate(RectifyNoticeCreateParam param) {
		RectifyNotice notice = EntityGenerator.newRectifyNoticeCreate(param);
		rectifyNoticeDao.insert(notice);
		return notice;
	}
	
	@Transactional
	public void rectifyNoticeFinish(LidParam param) { 
		Query query = new Query().and(Criteria.eq("id", param.getId())).forUpdate();
		RectifyNotice notice = Assert.notNull(rectifyNoticeDao.queryUnique(query), Codes.RECTIFY_NOTICE_NOT_EXIST);
		Assert.isTrue(notice.getState() == RectifyState.NEWLY, Code.FORBID);
		notice.setUpdated(DateUtil.current());
		notice.setState(RectifyState.FINISHED);
		rectifyNoticeDao.update(notice);
		// 删除这条整改的警告
		query = new Query().and(Criteria.in("type", AlertType.RECITIFY_NOTICE, AlertType.RECITIFY_NOTICE_EXPIRE), Criteria.eq("rectify_id", param.getId()));
		alertDao.deleteByQuery(query);
	}
	
	@Transactional
	public Inspect inspectCreate(String cid, long time, String content, long committer, List<Resource> resources) {
		Inspect inspect = EntityGenerator.newInspect(cid, time, content, committer);
		inspectDao.insert(inspect);
		if (!CollectionUtil.isEmpty(resources)) {
			resources.forEach(resource -> resource.setOwner(String.valueOf(inspect.getId())));
			resourceDao.insertMany(resources);
		}
		return inspect;
	}
	
	@Transactional
	public void alertCheck() { 
		log.info("开始单位状态...");
		// 获取新添加的整改通知
		List<Alert> alerts = new ArrayList<Alert>();
		Query query = new Query().and(Criteria.neq("state", RectifyState.NEWLY)).forUpdate();
		List<RectifyNotice> notices = rectifyNoticeDao.queryList(query);
		for (RectifyNotice notice : notices) {
			int now = DateUtil.current();
			AlertType type = now < notice.getClosingTime() ? AlertType.RECITIFY_NOTICE : AlertType.RECITIFY_NOTICE_EXPIRE;
			WarnLevel level = type == AlertType.RECITIFY_NOTICE ? notice.getWarnLevel() : WarnLevel.RED;
			Alert alert = EntityGenerator.newAlert(notice.getCid(), type, level, notice.getId());
			alerts.add(alert);
			notice.setUpdated(DateUtil.current());
			notice.setState(RectifyState.NOTIFIED);
		}
		if (!CollectionUtil.isEmpty(notices))
			rectifyNoticeDao.replaceCollection(notices);
	
		// 先清空所有的证书警告
		query = new Query().and(Criteria.in("type", AlertType.OPERATOR_CERT_EXPIRE_LIGHT, AlertType.OPERATOR_CERT_EXPIRE_SERIOUS));
		alertDao.deleteByQuery(query);
		// 获取所有的证书来判断证书的有效期
		List<OperatorCert> certs = operatorCertDao.selectList();
		if (!CollectionUtil.isEmpty(certs)) {
			MultiListMap<String, OperatorCert> map = new MultiListMap<String, OperatorCert>();
			// 将 operator_id、type、issue_agency作为联合主键作为作业人员唯一证书的判断
			certs.forEach(cert -> map.add(cert.getOperatorId() + "|" + cert.getType() + "|" + cert.getIssueAgency(), cert));
			map.values().forEach(l -> {
				OperatorCert choose = null;
				for (OperatorCert temp : l) {
					if (null == choose) {
						choose = temp;
						continue;
					}
					if (choose.getAuditType() == AuditType.TRIAL) {
						if (temp.getAuditType() == AuditType.REVIEW) {			// 有复审取复审
							choose = temp;
							continue;
						} else if (temp.getExpireTime() > choose.getExpireTime()) {	// 不是复审比较取时间最近的
							choose = temp;
							continue;
						}
					} else {
						if (temp.getAuditType() == AuditType.TRIAL)				// 如果是初审直接略过
							continue;
						else if (temp.getExpireTime() > choose.getExpireTime()) {
							choose = temp;
							continue;
						}
					}
				}
				if (choose.getExpireTime() <= DateUtil.current()) 				// 证书有效期过期
					alerts.add(EntityGenerator.newAlert(choose.getCid(), AlertType.OPERATOR_CERT_EXPIRE_SERIOUS, WarnLevel.RED, choose.getId()));
				else {
					int certThreshold = rubikConfigService.config(Constants.OPERATOR_CERT_THRESHOLD_DAY);
					long time = choose.getExpireTime() - certThreshold * DateUtil.DAY_SECONDS;
					if (DateUtil.current() >= time) 							// 当前日期大于等于下次检测日期-30天
						alerts.add(EntityGenerator.newAlert(choose.getCid(), AlertType.OPERATOR_CERT_EXPIRE_LIGHT, WarnLevel.YELLOW, choose.getId()));
				}
			});
		}
		log.info("单位状态更新结束！");
	}
	
	public Company company(String id) {
		return companyDao.selectByKey(id);
	}
	
	public Introspect introspect(long id) {
		return introspectDao.selectByKey(id);
	}
	
	public InspectDetail inspectDetail(long id) {
		return inspectDao.detail(id);
	}
	
	/**
	 * 获取有效的证书(判断证书有效期用)
	 */
	public List<OperatorCert> validOperatorCerts() {
		return operatorCertDao.validOperatorCerts();
	}
	
	public List<OperatorCert> operatorCerts(Query query) {
		return operatorCertDao.queryList(query);
	}
	
	public List<InspectInfo> inspects(InspectsParam param) {
		return inspectDao.list(param);
	}
	
	public List<CompanyInfo> companies(CompaniesParam param) { 
		return companyDao.list(param);
	}
	
	public List<OperatorInfo> operators(OperatorsParam param) { 
		return operatorDao.list(param);
	}

	public List<IntrospectInfo> introspects(IntrospectParam param) {
		return introspectDao.introspects(param);
	}
	
	public List<AlertStatistic> alertStatistic(AlertStatisticParam param) {
		return companyDao.alertStatistic(param);
	}
	
	public List<RectifyNoticeInfo> rectifyNotices(RectifyNoticesParam param) {
		return rectifyNoticeDao.list(param);
	}
}
