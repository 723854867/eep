package org.eep.common.bean.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyTitle implements Serializable {

	private static final long serialVersionUID = -8043309694871488680L;

	private String id;
	private String name;
}
