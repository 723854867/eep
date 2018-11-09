package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.eep.common.bean.enums.RectifyState;
import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RectifyNotice implements Identifiable<Long> {

	private static final long serialVersionUID = -7375723732610337795L;

	@Id
	@GeneratedValue
	private long id;
	private String cid;
	private int created;
	private int updated;
	private String problem;
	private String measure;
	private long committer;
	private int closingTime;
	private RectifyState state;
	private WarnLevel warnLevel;
	private String deregulation;
	private String processBasis;
	
	@Override
	public Long key() {
		return this.id;
	}
}
