package org.eep.service;

import java.util.List;

import javax.annotation.Resource;

import org.eep.common.bean.entity.Api;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.LoginInfo;
import org.eep.common.bean.model.UserInfo;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.LoginParam;
import org.eep.common.bean.param.PwdModifyParam;
import org.eep.common.bean.param.UserCreateParam;
import org.eep.common.bean.param.UserModifyParam;
import org.eep.common.bean.param.UsersParam;
import org.eep.manager.CompanyManager;
import org.eep.manager.RegionManager;
import org.eep.manager.UserManager;
import org.rubik.bean.core.model.Pager;
import org.rubik.bean.core.param.Param;
import org.rubik.mybatis.PagerUtil;
import org.rubik.util.common.StringUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class UserService {

	@Resource
	private UserManager userManager;
	@Resource
	private RegionManager regionManager;
	@Resource
	private CompanyManager companyManager;
	
	public User user(long uid) {
		return userManager.user(uid);
	}
	
	public void logout(Param param) {
		userManager.logout(param);
	}
	
	public boolean releaseLock(Visitor visitor) { 
		return userManager.releaseLock(visitor);
	}
	
	public LoginInfo login(LoginParam param) {
		return userManager.login(param);
	}
	
	public User create(UserCreateParam param) {
		return userManager.create(param);
	}
	
	public void modify(UserModifyParam param) {
		userManager.modify(param);
	}
	
	public void pwdModify(PwdModifyParam param) {
		userManager.pwdModify(param);
	}
	
	public Visitor visitor(Api api, String token) {
		Visitor visitor = null;
		if (null == api || !api.isLock()) 
			visitor = userManager.getVisitorByToken(api, token);
		else 
			visitor = userManager.lockVisitorByToken(api, token);
		if (StringUtil.hasText(visitor.getUser().getCid())) {
			CompanyInfo company = companyManager.company(visitor.getUser().getCid());
			visitor.setCompany(company);
		}
		if (0 != visitor.getUser().getRegion()) {
			SysRegion region = regionManager.region(visitor.getUser().getRegion());
			visitor.setRegion(region);
		}
		return visitor;
	}
	
	public List<UserInfo> getByCid(String cid) { 
		UsersParam param = new UsersParam();
		param.setCid(cid);
		return userManager.users(param);
	}
	
	public Pager<UserInfo> list(UsersParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(userManager.users(param));
	}
}
