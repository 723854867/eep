package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repair implements Identifiable<Long> {

	private static final long serialVersionUID = 6669993732310202627L;

	@Id
	@GeneratedValue
	private long id;
	private String cid;
	private String rid;
	private int created;
	private String content;
	private long committer;
	
	@Override
	public Long key() {
		return this.id;
	}
}
