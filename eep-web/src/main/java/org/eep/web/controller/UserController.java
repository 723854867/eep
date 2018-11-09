package org.eep.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.eep.common.Codes;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.UserInfo;
import org.eep.common.bean.param.LoginParam;
import org.eep.common.bean.param.PwdModifyParam;
import org.eep.common.bean.param.UserCreateParam;
import org.eep.common.bean.param.UsersParam;
import org.eep.service.RegionService;
import org.eep.service.UserService;
import org.eep.util.RegionUtil;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private RegionService regionService;

	@ResponseBody
	@RequestMapping("login")
	public Object login(@RequestBody @Valid LoginParam param) {
		return userService.login(param);
	}
	
	@ResponseBody
	@RequestMapping("logout")
	public Object logout(@RequestBody @Valid Param param) {
		userService.logout(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("list")
	public Object list(@RequestBody @Valid UsersParam param) {
		if (null != param.getRegion()) 
			RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return userService.list(param);
	}
	
	@ResponseBody
	@RequestMapping("list/area")
	public Object listArea(@RequestBody @Valid UsersParam param) {
		Assert.notNull(param.getRegion(), Code.PARAM_ERR, "param region is null");
		regionService.userRegionVerify(param.requestor().id(), param.getRegion());
		RegionUtil.setRange(param, Assert.notNull(regionService.region(param.getRegion()), Codes.REGION_NOT_EXIST));
		return userService.list(param);
	}
	
	@ResponseBody
	@RequestMapping("create")
	public Object create(@RequestBody @Valid UserCreateParam param) {
		User user = userService.create(param);
		return new UserInfo(user);
	}
	
	@ResponseBody
	@RequestMapping("pwd/modify")
	public Object pwdModify(@RequestBody @Valid PwdModifyParam param) {
		userService.pwdModify(param);
		return Result.ok();
	}
}
