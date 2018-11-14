package org.eep.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.eep.common.bean.entity.User;
import org.eep.service.UserService;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.Param;
import org.rubik.bean.core.param.SidParam;
import org.rubik.soa.authority.api.AuthorityService;
import org.rubik.soa.authority.bean.param.AuthParam;
import org.rubik.soa.authority.bean.param.ModularAddParam;
import org.rubik.soa.authority.bean.param.ModularModifyParam;
import org.rubik.soa.authority.bean.param.RoleAddParam;
import org.rubik.soa.authority.bean.param.RoleModifyParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("authority")
public class AuthorityController {

	@Resource
	AuthorityService authorityService;
	@Resource
	UserService userService;
	
	@ResponseBody
	@RequestMapping("module/create")
	public Object addModule(@RequestBody @Valid ModularAddParam param) { 
		return authorityService.addModule(param);
	}
	
	@ResponseBody
	@RequestMapping("module/modify")
	public Object modifyModule(@RequestBody @Valid ModularModifyParam param) { 
		authorityService.modifyModule(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("module/delete")
	public Object deleteModule(@RequestBody @Valid SidParam param) { 
		authorityService.deleteModule(Integer.valueOf(param.getId()));
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("module/list")
	public Object cfgModules(@RequestBody @Valid Param param) { 
		return authorityService.cfgModules();
	}
	
	@ResponseBody
	@RequestMapping("module/user/list")
	public Object cfgModules(@RequestBody @Valid SidParam param) { 
		return authorityService.cfgModules(Integer.valueOf(param.getId()));
	}
	
	@ResponseBody
	@RequestMapping("role/create")
	public Object addRole(@RequestBody @Valid RoleAddParam param) { 
		return authorityService.addRole(param);
	}
	
	@ResponseBody
	@RequestMapping("role/modify")
	public Object modifyRole(@RequestBody @Valid RoleModifyParam param) { 
		authorityService.modifyRole(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("role/delete")
	public Object deleteRole(@RequestBody @Valid SidParam param) { 
		authorityService.deleteRole(Integer.valueOf(param.getId()));
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("role/list")
	public Object cfgRoles(@RequestBody @Valid Param param) { 
		return authorityService.cfgRoles();
	}
	
	@ResponseBody
	@RequestMapping("role/user/list")
	public Object cfgRoles(@RequestBody @Valid SidParam param) { 
		return authorityService.userRoles(Integer.valueOf(param.getId()));
	}
	
	/*
	 * 给用户分配模块
	 */
	@ResponseBody
	@RequestMapping("auth/module")
	public Object modularAuth(@RequestBody @Valid AuthParam param) { 
		authorityService.modularAuth(param);
		return Result.ok();
	}
	
	/*
	 * 给用户分配角色
	 */
	@ResponseBody
	@RequestMapping("auth/role")
	public Object cfgRoles(@RequestBody @Valid AuthParam param) { 
		User user = userService.user(param.getSid());
		Assert.notNull(user,Code.USER_NOT_EIXST);
		authorityService.roleAuth(param);
		return Result.ok();
	}

}
