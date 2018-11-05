package org.eep.web.hook;

import javax.annotation.Resource;

import org.eep.common.Codes;
import org.eep.common.Consts;
import org.eep.common.bean.entity.Api;
import org.eep.common.bean.model.Visitor;
import org.eep.service.AuthService;
import org.eep.service.CompanyService;
import org.eep.service.UserService;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.model.RequestMeta;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.util.common.StringUtil;
import org.rubik.web.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class GatewayHook implements org.rubik.web.GatewayHook {
	
	@Resource
	private UserService userService;
	@Resource
	private AuthService authService;
	@Resource
	private CompanyService companyService;
	@Resource
	private RubikConfigService rubikConfigService;

	@Override
	public void postInvoke(RequestMeta meta) {
		Api api = authService.api(meta.getPath());
		if (null != api) {
			meta.setApiDesc(api.getDesc());
			RequestContext.put("api", api);
		}
		int apiSecurityLevel = null == api ? 1 : api.getSecurityLevel();
		int securityLevel = rubikConfigService.config(Consts.SERVER_SECURITY_LEVEL);
		Assert.isTrue(apiSecurityLevel >= securityLevel, Codes.API_MAINTENANCE);
		String token = meta.getStringHeader("token");
		Visitor visitor = StringUtil.hasText(token) ? userService.visitor(api, token) : null;
		try {
			meta.setRequestor(visitor);
			if (null != api && api.isLogin())
				Assert.notNull(visitor, Code.UNLOGIN);
			Long employeeId = meta.getLongHeader("employeeid");
			if (null != employeeId)
				companyService.visitorSetup(visitor, employeeId);
		} finally {
			if (null != api && api.isLock()) 
				userService.releaseLock(visitor);
		}
	}

	@Override
	public void afterInvoke(RequestMeta meta, Object[] params, Object response) {
		
	}
}
