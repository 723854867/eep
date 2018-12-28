package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.Company;
import org.eep.common.bean.model.AlertStatistic;
import org.eep.common.bean.model.CompanyInfo;
import org.eep.common.bean.model.CompanyTitle;
import org.eep.common.bean.param.AlertStatisticParam;
import org.eep.common.bean.param.CompaniesParam;
import org.rubik.mybatis.extension.Dao;

public interface CompanyDao extends Dao<String, Company> {
	
	CompanyInfo info(String id);

	List<CompanyInfo> list(CompaniesParam param);
	
	//只查询名称和id
	List<CompanyTitle> titles(CompaniesParam param);
	
	List<AlertStatistic> alertStatistic(AlertStatisticParam param);
}
