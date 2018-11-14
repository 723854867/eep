package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Repair;
import org.eep.common.bean.model.RepairDetail;
import org.eep.common.bean.model.RepairInfo;
import org.eep.common.bean.param.RepairsParam;
import org.rubik.mybatis.extension.Dao;

public interface RepairDao extends Dao<Long, Repair> {

	RepairDetail detail(long id);
	
	List<RepairInfo> list(RepairsParam param);
}
