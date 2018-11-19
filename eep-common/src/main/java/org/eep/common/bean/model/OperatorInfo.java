package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.Sex;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorInfo implements Serializable {

	private static final long serialVersionUID = -6077761657940699365L;

	private Sex sex;
	private String id;
	private String cid;
	private String cname;
	private String name;
	private int updated;
	private String phone;
	private String mobile;
	private String address;
	private String identity;
	private String birthday;
	private String education;
	private String cerExpire;
}
