package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegion implements Identifiable<Long> {

	private static final long serialVersionUID = 2468095917279057730L;
	
	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private long region;
	private int created;

	@Override
	public Long key() {
		return this.id;
	}
}
