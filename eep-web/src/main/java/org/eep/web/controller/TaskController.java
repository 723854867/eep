package org.eep.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.eep.sync.service.SyncService;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("task")
public class TaskController {
	
	@Resource
	private SyncService syncService;

	@ResponseBody
	@RequestMapping("sync")
	public Object sync(@RequestBody @Valid Param param) {
		syncService.sync();
		return Result.ok();
	}
}
