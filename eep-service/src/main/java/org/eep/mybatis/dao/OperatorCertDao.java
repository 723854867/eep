package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.OperatorCert;
import org.rubik.mybatis.extension.Dao;

public interface OperatorCertDao extends Dao<String, OperatorCert> {

	List<OperatorCert> validOperatorCerts();
}
