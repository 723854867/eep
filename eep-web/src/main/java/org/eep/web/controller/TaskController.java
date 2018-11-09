package org.eep.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.eep.common.Codes;
import org.eep.service.CompanyService;
import org.eep.service.DeviceService;
import org.eep.sync.service.SyncService;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.Param;
import org.rubik.redis.Locker;
import org.rubik.util.common.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("task")
public class TaskController {
	
	private static final String ALERT_KEY			= "string:alert:locker";
	
	@Resource
	private Locker locker;
	@Resource
	private SyncService syncService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private CompanyService companyService;

	@ResponseBody
	@RequestMapping("sync")
	public Object sync(@RequestBody @Valid Param param) {
		syncService.sync();
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("alert/check")
	public Object alertCheck(@RequestBody @Valid Param param) {
		String lockId = locker.tryLock(ALERT_KEY, DateUtil.MINUTE_SECONDS * 10 * 1000);
		Assert.hasText(lockId, Codes.DEVICE_ALERT_TASK_RUNNING);
		try {
			deviceService.alertCheck();
			companyService.alertCheck();
		} finally {
			locker.releaseLock(ALERT_KEY, lockId);
		}
		return Result.ok();
	}
}
