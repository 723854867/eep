package org.eep.common.bean.param;

import java.util.ArrayList;
import java.util.List;

import org.rubik.bean.core.model.Criteria;
import org.rubik.bean.core.model.Query;
import org.rubik.bean.core.param.Param;
import org.rubik.util.common.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorCertsParam extends Param {

	private static final long serialVersionUID = -7536352372805893719L;
	
	private String id;
	private String certno;
	private String operatorId;
	
	@Override
	public Query getQuery() {
		Query query = super.getQuery();
		List<Criteria> criterias = new ArrayList<Criteria>();
		if (StringUtil.hasText(id))
			criterias.add(Criteria.eq("id", id));
		if (StringUtil.hasText(certno))
			criterias.add(Criteria.eq("certno", certno));
		if (StringUtil.hasText(operatorId))
			criterias.add(Criteria.eq("operator_id", operatorId));
		return query.and(criterias);
	}

	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
