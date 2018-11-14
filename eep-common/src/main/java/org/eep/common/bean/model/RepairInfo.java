package org.eep.common.bean.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairInfo implements Serializable {

	private static final long serialVersionUID = 3887645775018115629L;

	private long id;
	private int time;
	private String cid;
	private String rid;
	private String cname;
	private String rname;
	private long nextTime;
}
