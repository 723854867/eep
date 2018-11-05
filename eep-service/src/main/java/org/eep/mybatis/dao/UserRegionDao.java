package org.eep.mybatis.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.eep.common.bean.entity.SysRegion;
import org.eep.common.bean.entity.UserRegion;
import org.rubik.mybatis.extension.Dao;

public interface UserRegionDao extends Dao<Long, UserRegion> {
	
	@Select("SELECT region FROM user_region WHERE uid=#{uid} FOR UPDATE")
	Set<Long> lockUserRegions(long uid);
	
	@Select("SELECT b.* FROM user_region a LEFT JOIN sys_region b ON a.region=b.id WHERE a.uid=#{uid}")
	List<SysRegion> regions(long uid);
}
