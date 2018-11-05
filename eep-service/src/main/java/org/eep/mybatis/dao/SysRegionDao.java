package org.eep.mybatis.dao;

import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.eep.common.bean.entity.SysRegion;
import org.rubik.mybatis.extension.Dao;

public interface SysRegionDao extends Dao<Long, SysRegion> {

	@Select("SELECT MAX(id) FROM sys_region")
	Long max(); 
	
	@MapKey("id")
	@Select("SELECT * FROM sys_region WHERE trunk=(SELECT trunk FROM sys_region WHERE id=#{id}) FOR UPDATE")
	Map<Long, SysRegion> lockTrunkById(long id);
}
