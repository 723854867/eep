package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.User;
import org.eep.common.bean.model.UserInfo;
import org.eep.common.bean.param.UsersParam;
import org.rubik.mybatis.extension.Dao;

public interface UserDao extends Dao<Long, User> {

	List<UserInfo> list(UsersParam param); 
}
