package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Operator;
import org.eep.common.bean.model.OperatorInfo;
import org.eep.common.bean.param.OperatorsParam;
import org.rubik.mybatis.extension.Dao;

public interface OperatorDao extends Dao<String, Operator> {

	List<OperatorInfo> list(OperatorsParam param);
}
