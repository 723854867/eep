package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Employee;
import org.eep.common.bean.model.EmployeeInfo;
import org.eep.common.bean.param.EmployeesParam;
import org.rubik.mybatis.extension.Dao;

public interface EmployeeDao extends Dao<Long, Employee> {

	List<EmployeeInfo> list(EmployeesParam param);
}
