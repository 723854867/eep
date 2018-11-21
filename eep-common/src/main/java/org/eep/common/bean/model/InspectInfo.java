package org.eep.common.bean.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectInfo implements Serializable {

	private static final long serialVersionUID = 3887645775018115629L;

	private long id;
	private long time;
	private String cid;
	private int created;
	private String cname;
}
