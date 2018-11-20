package org.eep.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.eep.common.bean.entity.Company;
import org.eep.common.bean.model.AlertStatistic;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.CompanyTitle;
import org.eep.common.bean.param.AlertStatisticParam;
import org.eep.common.bean.param.CompaniesParam;
import org.rubik.mybatis.extension.Dao;

public interface CompanyDao extends Dao<String, Company> {

	List<CompanyInfo> list(CompaniesParam param);
	
	//只查询名称和id
	@Select("SELECT id , NAME FROM company WHERE `type` = 1")
	List<CompanyTitle> titles();
	
	List<AlertStatistic> alertStatistic(AlertStatisticParam param);
}
