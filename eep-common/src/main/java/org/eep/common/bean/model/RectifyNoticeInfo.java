package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.RectifyState;
import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RectifyNoticeInfo implements Serializable {

	private static final long serialVersionUID = 125346504469779297L;

	private long id;
	private String cid;
	private int created;
	private int updated;
	private String cname;
	private String problem;
	private String measure;
	private long committer;
	private int closingTime;
	private RectifyState state;
	private WarnLevel warnLevel;
}
