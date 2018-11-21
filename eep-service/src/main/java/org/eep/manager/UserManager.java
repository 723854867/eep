package org.eep.manager;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.eep.common.Codes;
import org.eep.common.bean.entity.Api;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.entity.UserToken;
import org.eep.common.bean.model.LoginInfo;
import org.eep.common.bean.model.UserInfo;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.LoginParam;
import org.eep.common.bean.param.PwdModifyParam;
import org.eep.common.bean.param.UserCreateParam;
import org.eep.common.bean.param.UserModifyParam;
import org.eep.common.bean.param.UsersParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.UserDao;
import org.eep.mybatis.dao.UserTokenDao;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.exception.AssertException;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.Param;
import org.rubik.redis.Locker;
import org.rubik.util.common.DateUtil;
import org.rubik.util.common.StringUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserManager {
	
	private static final String USER_LOCK_KEY				= "string:lock:user:{0}";
	
	@Resource
	private Locker locker;
	@Resource
	private UserDao userDao;
	@Resource
	private UserTokenDao userTokenDao;
	@Resource
	private RegionManager regionManager;
	@Resource
	private CompanyManager companyManager;
	
	public void logout(Param param) { 
		Visitor visitor = param.requestor();
		userTokenDao.deleteByQuery(new Query().and(Criteria.eq("token", visitor.getToken())));
	}
	
	@Transactional
	public LoginInfo login(LoginParam param) {
		Query query = new Query().and(Criteria.eq("uname", param.getUname())).forUpdate();
		User user = Assert.notNull(userDao.queryUnique(query), Codes.UNAME_NOT_EXIST);
		String pwd = DigestUtils.md5Hex(param.getPassword() + "_" + user.getSalt());
		Assert.isTrue(pwd.equals(user.getPwd()), Codes.PWD_LOGIN_ERR);
		userTokenDao.deleteByQuery(new Query().and(Criteria.eq("device", param.getDevice()), Criteria.eq("uid", user.getId())));
		UserToken token = EntityGenerator.newUserToken(user, param);
		userTokenDao.insert(token);
		Company company = StringUtil.hasText(user.getCid()) ? companyManager.company(user.getCid()) : null;
		SysRegion region = 0 == user.getRegion() ? null : regionManager.region(user.getRegion());
		return new LoginInfo(user, company, region, token);
	}
	
	public User create(UserCreateParam param) { 
		User user = EntityGenerator.newUser(param);
		try {
			userDao.insert(user);
		} catch (DuplicateKeyException e) {
			throw AssertException.error(Code.UNAME_EXIST);
		}
		return user;
	}
	
	public void modify(UserModifyParam param) {
		User user = userDao.selectByKey(param.getId());
		Assert.notNull(user, Codes.UNAME_NOT_EXIST);
		if(null != param.getMobile())
			user.setMobile(param.getMobile());
		if(null != param.getNickname())
			user.setNickname(param.getNickname());
		if(null != param.getCornette())
			user.setCornette(param.getCornette());
		if(null != param.getPassword())
			user.setPwd(DigestUtils.md5Hex(param.getPassword() + "_" + user.getSalt()));
		user.setUpdated(DateUtil.current());
		userDao.update(user);
	}
	
	public void pwdModify(PwdModifyParam param) {
		Visitor visitor = param.requestor();
		User user = visitor.getUser();
		String pwd = DigestUtils.md5Hex(param.getOpassword() + "_" + user.getSalt());
		Assert.isTrue(pwd.equals(user.getPwd()), Codes.PWD_LOGIN_ERR);
		user.setUpdated(DateUtil.current());
		user.setPwd(DigestUtils.md5Hex(param.getNpassword() + "_" + user.getSalt()));
		userDao.update(user);
	}
	
	public Visitor getVisitorByToken(Api api, String token) { 
		UserToken userToken = Assert.notNull(userTokenDao.selectByKey(token), Code.TOKEN_INVALID);
		User user = userDao.selectByKey(userToken.getUid());
		return new Visitor(user, userToken);
	}
	
	@Transactional
	public Visitor lockVisitorByToken(Api api, String token) { 
		Query query = new Query().and(Criteria.eq("token", token)).forUpdate();
		UserToken userToken = Assert.notNull(userTokenDao.queryUnique(query), Code.TOKEN_INVALID);
		String key = MessageFormat.format(USER_LOCK_KEY, String.valueOf(userToken.getUid()));
		String lockId = Assert.hasText(locker.lock(key, api.getLockTimeout(), api.getLockExpire()), Code.USER_LOCKED);
		User user = userDao.selectByKey(userToken.getUid());
		return new Visitor(lockId, user, userToken);
	}
	
	public boolean releaseLock(Visitor visitor) {
		String key = MessageFormat.format(USER_LOCK_KEY, String.valueOf(visitor.id()));
		return locker.releaseLock(key, visitor.getLockId());
	}
	
	void update(User user) {
		userDao.update(user);
	}
	
	void deleteRegion(long min, long max) { 
		userDao.deleteRegion(min, max);
	}
	
	public User user(long uid) {
		return userDao.selectByKey(uid);
	}
	
	public List<UserInfo> users(UsersParam param) {
		return userDao.list(param);
	}
}
