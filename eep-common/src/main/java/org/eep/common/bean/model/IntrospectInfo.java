package org.eep.common.bean.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntrospectInfo {
	
	private long id;
	//单位名称
	private String name;
	//法人
	private String legalPerson;
	//填表人
	private String filler;
	//填表时间
	private int created;
	//填报人电话
	private String fillerPhone;
	private String content;
}
