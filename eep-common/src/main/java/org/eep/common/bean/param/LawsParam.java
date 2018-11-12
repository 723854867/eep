package org.eep.common.bean.param;

import java.util.ArrayList;
import java.util.List;

import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.Param;
import org.rubik.util.common.CollectionUtil;
import org.rubik.util.common.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawsParam extends Param {

	private static final long serialVersionUID = 6495191565276607484L;

	private String title;
	private Integer categoryId;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
		Query query = getQuery();
		List<Criteria> criterias = new ArrayList<Criteria>();
		if (null != categoryId)
			criterias.add(Criteria.eq("category_id", categoryId));
		if (StringUtil.hasText(title))
			criterias.add(Criteria.like("title", title));
		if (CollectionUtil.isEmpty(criterias))
			query.and(criterias);
	}
}
