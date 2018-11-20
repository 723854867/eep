package org.eep.common.bean.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.eep.common.bean.enums.CompanyType;
import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyInfo_ implements Serializable {

	private static final long serialVersionUID = -8043309694871488680L;

	private String id;
	private String name;
}
