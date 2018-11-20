package org.eep.service;

import javax.annotation.Resource;

import org.eep.common.bean.entity.Law;
import org.eep.common.bean.entity.LawCategory;
import org.eep.common.bean.param.LawCategoryCreateParam;
import org.eep.common.bean.param.LawCategoryModifyParam;
import org.eep.common.bean.param.LawCreateParam;
import org.eep.common.bean.param.LawModifyParam;
import org.eep.common.bean.param.LawsParam;
import org.eep.manager.CommonManager;
import org.rubik.bean.core.model.Pager;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.IdParam;
import org.rubik.mybatis.PagerUtil;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class CommonService {

	@Resource
	private CommonManager commonManager;
	
	public LawCategory lawCategoryCreate(LawCategoryCreateParam param) {
		return commonManager.lawCategoryCreate(param);
	}
	
	public void lawCategoryModify(LawCategoryModifyParam param) {
		commonManager.lawCategoryModify(param);
	}
	
	public void lawCategoryDelete(IdParam param) {
		commonManager.lawCategoryDelete(param);
	}
	
	public Law lawCreate(LawCreateParam param) { 
		return commonManager.lawCreate(param);
	}
	
	public void lawModify(LawModifyParam param) { 
		commonManager.lawModify(param);
	}
	
	public void lawDelete(IdParam param) { 
		commonManager.lawDelete(param);
	}

	public Pager<Law> laws(LawsParam param) { 
		if (null != param.getPage())
			PageHelper.startPage(param.getPage(), param.getPageSize());
		return PagerUtil.page(commonManager.laws(param));
	}
	
	public Pager<LawCategory> lawCategories(Query query) { 
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return PagerUtil.page(commonManager.lawCategories(query));
	}
	
	public Pager<org.eep.common.bean.entity.Resource> resources(Query query) { 
		if (null != query.getPage())
			PageHelper.startPage(query.getPage(), query.getPageSize());
		return PagerUtil.page(commonManager.resources(query));
	}
}
