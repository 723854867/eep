package org.eep.service;

import javax.annotation.Resource;

import org.eep.common.bean.entity.Api;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.LoginInfo;
import org.eep.common.bean.model.UserInfo;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.LoginParam;
import org.eep.common.bean.param.PwdModifyParam;
import org.eep.common.bean.param.UserCreateParam;
import org.eep.common.bean.param.UsersParam;
import org.eep.manager.UserManager;
import org.rubik.bean.core.model.Pager;
import org.rubik.bean.core.param.Param;
import org.rubik.mybatis.PagerUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class UserService {

	@Resource
	private UserManager userManager;
	
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
	
	public void pwdModify(PwdModifyParam param) {
		userManager.pwdModify(param);
	}
	
	public Visitor visitor(Api api, String token) {
		if (null == api || !api.isLock()) 
			return userManager.getVisitorByToken(api, token);
		else 
			return userManager.lockVisitorByToken(api, token);
	}
	
	public Pager<UserInfo> list(UsersParam param) {
		PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(userManager.users(param));
	}
}
