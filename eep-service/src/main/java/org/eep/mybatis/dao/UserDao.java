package org.eep.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.UserInfo;
import org.eep.common.bean.param.UsersParam;
import org.rubik.mybatis.extension.Dao;

public interface UserDao extends Dao<Long, User> {

	List<UserInfo> list(UsersParam param); 
	
	@Update("UPDATE user SET region=0 WHERE region>=#{min} AND region<=#{max}")
	void deleteRegion(@Param("min") long min, @Param("max") long max);
}
