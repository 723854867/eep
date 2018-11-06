package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Introspect;
import org.eep.common.bean.model.IntrospectInfo;
import org.eep.common.bean.param.IntrospectParam;
import org.rubik.mybatis.extension.Dao;

public interface IntrospectDao extends Dao<Long, Introspect> {

	List<IntrospectInfo> introspects(IntrospectParam param);
}
