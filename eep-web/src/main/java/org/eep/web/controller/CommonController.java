package org.eep.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.eep.common.bean.param.LawCategoryCreateParam;
import org.eep.common.bean.param.LawCategoryModifyParam;
import org.eep.common.bean.param.LawCreateParam;
import org.eep.common.bean.param.LawModifyParam;
import org.eep.common.bean.param.LawsParam;
import org.eep.service.CommonService;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.model.Result;
import org.rubik.bean.core.param.IdParam;
import org.rubik.bean.core.param.Param;
import org.rubik.soa.config.api.RubikConfigService;
import org.rubik.soa.config.bean.model.RubikConfigs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("common")
public class CommonController {
	
	@Resource
	private CommonService commonService;
	@Resource
	private RubikConfigService rubikConfigService;

	@ResponseBody
	@RequestMapping("configs/visible")
	public Object configsVisible(@RequestBody @Valid Param param) { 
		Query query = new Query().and(Criteria.eq("visible", true));
		RubikConfigs configs = rubikConfigService.config(query);
		Map<String, String> map = new HashMap<String, String>();
		configs.entrySet().forEach(entry -> map.put(entry.getKey(), entry.getValue().getValue()));
		return map;
	}
	
	@ResponseBody
	@RequestMapping("law/categories")
	public Object lawCategories(@RequestBody @Valid Param param) { 
		return commonService.lawCategories(param.getQuery());
	}
	
	@ResponseBody
	@RequestMapping("law/category/create")
	public Object lawCategoryCreate(@RequestBody @Valid LawCategoryCreateParam param) {
		return commonService.lawCategoryCreate(param);
	}
	
	@ResponseBody
	@RequestMapping("law/category/modify")
	public Object lawCategoryModify(@RequestBody @Valid LawCategoryModifyParam param) {
		commonService.lawCategoryModify(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("law/category/delete")
	public Object lawCategoryDelete(@RequestBody @Valid IdParam param) {
		commonService.lawCategoryDelete(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("law/list")
	public Object laws(@RequestBody @Valid LawsParam param) { 
		return commonService.laws(param.getQuery());
	}
	
	@ResponseBody
	@RequestMapping("law/create")
	public Object lawCreate(@RequestBody @Valid LawCreateParam param) { 
		return commonService.lawCreate(param);
	}
	
	@ResponseBody
	@RequestMapping("law/modify")
	public Object lawModify(@RequestBody @Valid LawModifyParam param) { 
		commonService.lawModify(param);
		return Result.ok();
	}
	
	@ResponseBody
	@RequestMapping("law/delete")
	public Object lawDelete(@RequestBody @Valid IdParam param) { 
		commonService.lawDelete(param);
		return Result.ok();
	}
}
