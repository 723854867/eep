package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inspect implements Identifiable<Long> {

	private static final long serialVersionUID = 7981978357043482968L;
	
	@Id
	@GeneratedValue
	private long id;
	private long time;
	private String cid;
	private int created;
	private long committer;
	private String content;

	@Override
	public Long key() {
		return this.id;
	}
}
