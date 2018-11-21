package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Alert;
import org.eep.common.bean.model.AlertInfo;
import org.eep.common.bean.param.AlertsParam;
import org.rubik.mybatis.extension.Dao;

public interface AlertDao extends Dao<Long, Alert> {

	List<AlertInfo> list(AlertsParam param);
}
