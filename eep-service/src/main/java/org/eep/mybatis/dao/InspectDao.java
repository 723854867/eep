package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Inspect;
import org.eep.common.bean.model.InspectDetail;
import org.eep.common.bean.model.InspectInfo;
import org.eep.common.bean.param.InspectsParam;
import org.rubik.mybatis.extension.Dao;

public interface InspectDao extends Dao<Long, Inspect> {
	
	InspectDetail detail(long id);

	List<InspectInfo> list(InspectsParam param);
}
