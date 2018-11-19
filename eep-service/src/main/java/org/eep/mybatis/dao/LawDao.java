package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Law;
import org.eep.common.bean.param.LawsParam;
import org.rubik.mybatis.extension.Dao;

public interface LawDao extends Dao<Integer, Law> {

	List<Law> list(LawsParam param);
}
