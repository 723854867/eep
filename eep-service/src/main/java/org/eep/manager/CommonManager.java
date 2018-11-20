package org.eep.manager;

import java.util.List;

import javax.annotation.Resource;

import org.eep.common.Codes;
import org.eep.common.bean.entity.Law;
import org.eep.common.bean.entity.LawCategory;
import org.eep.common.bean.param.LawCategoryCreateParam;
import org.eep.common.bean.param.LawCategoryModifyParam;
import org.eep.common.bean.param.LawCreateParam;
import org.eep.common.bean.param.LawModifyParam;
import org.eep.common.bean.param.LawsParam;
import org.eep.mybatis.EntityGenerator;
import org.eep.mybatis.dao.LawCategoryDao;
import org.eep.mybatis.dao.LawDao;
import org.eep.mybatis.dao.ResourceDao;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.IdParam;
import org.rubik.util.common.DateUtil;
import org.rubik.util.common.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommonManager {

	@Resource
	private LawDao lawDao;
	@Resource
	private ResourceDao resourceDao;
	@Resource
	private LawCategoryDao lawCategoryDao;
	
	public LawCategory lawCategoryCreate(LawCategoryCreateParam param) {
		LawCategory instance = EntityGenerator.newLawCategory(param);
		lawCategoryDao.insert(instance);
		return instance;
	}
	
	@Transactional
	public void lawCategoryModify(LawCategoryModifyParam param) {
		Query query = new Query().and(Criteria.eq("id", param.getId())).forUpdate();
		LawCategory category = Assert.notNull(lawCategoryDao.queryUnique(query), Codes.LAW_CATEGORY_NOT_EXIST);
		category.setName(param.getName());
		category.setUpdated(DateUtil.current());
		lawCategoryDao.update(category);
	}
	
	@Transactional
	public void lawCategoryDelete(IdParam param) {
		Assert.isTrue(1 == lawCategoryDao.deleteByKey(param.getId()), Codes.LAW_CATEGORY_NOT_EXIST);
		Query query = new Query().and(Criteria.eq("category_id", param.getId()));
		lawDao.deleteByQuery(query);
	}
	
	@Transactional
	public Law lawCreate(LawCreateParam param) { 
		Query query = new Query().and(Criteria.eq("id", param.getCategoryId())).forUpdate();
		Assert.notNull(lawCategoryDao.queryUnique(query), Codes.LAW_CATEGORY_NOT_EXIST);
		Law law = EntityGenerator.newLaw(param);
		lawDao.insert(law);
		return law;
	}
	
	@Transactional
	public void lawModify(LawModifyParam param) { 
		Law law = Assert.notNull(lawDao.selectByKey(param.getId()), Codes.LAW_NOT_EXIST);
		if (null != param.getCategoryId()) {
			Query query = new Query().and(Criteria.eq("id", param.getCategoryId())).forUpdate();
			Assert.notNull(lawCategoryDao.queryUnique(query), Codes.LAW_CATEGORY_NOT_EXIST);
			law.setCategoryId(param.getCategoryId());
		}
		if (StringUtil.hasText(param.getTitle()))
			law.setTitle(param.getTitle());
		if (StringUtil.hasText(param.getContent()))
			law.setContent(param.getContent());
		law.setUpdated(DateUtil.current());
		lawDao.update(law);
	}
	
	@Transactional
	public void lawDelete(IdParam param) { 
		lawDao.deleteByKey(param.getId());
	}
	
	public List<Law> laws(LawsParam param) {
		return lawDao.list(param);
	}
	
	public List<LawCategory> lawCategories(Query query) {
		return lawCategoryDao.queryList(query);
	}
	
	public List<org.eep.common.bean.entity.Resource> resources(Query query) {
		return resourceDao.queryList(query);
	}
}
