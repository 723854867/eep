package org.eep.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.eep.bean.param.RegionActParam;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.Visitor;
import org.eep.common.bean.param.RegionCreateParam;
import org.eep.common.bean.param.RegionModifyParam;
import org.eep.service.RegionService;
import org.eep.service.UserService;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.LidParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("region")
public class RegionController {
	
	@Resource
	private UserService userService;
	@Resource
	private RegionService regionService;

	@ResponseBody
	@RequestMapping("create")
	public Object create(@RequestBody @Valid RegionCreateParam param) { 
		return regionService.create(param);
	}
	
	@ResponseBody
	@RequestMapping("modify")
	public Object modify(@RequestBody @Valid RegionModifyParam param) {
		regionService.modify(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(@RequestBody @Valid LidParam param) { 
		regionService.delete(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("grant")
	public Object grant(@RequestBody @Valid RegionActParam param) {
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.id() != param.getUid(), Code.FORBID);
		User grantee = Assert.notNull(userService.user(param.getUid()), Code.USER_NOT_EIXST);
		regionService.grant(visitor.getUser(), grantee, param.getRegion());
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("reclaim")
	public Object reclaim(@RequestBody @Valid LidParam param) {
		Visitor visitor = param.requestor();
		Assert.isTrue(visitor.id() != param.getId(), Code.FORBID);
		User reclaimee = Assert.notNull(userService.user(param.getId()), Code.USER_NOT_EIXST);
		regionService.reclaim(visitor.getUser(), reclaimee);
		return Result.ok();
	}
}
