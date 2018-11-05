package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.eep.common.bean.enums.EmployeeState;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee implements Identifiable<Long> {

	private static final long serialVersionUID = -3667877885741609548L;
	
	@Id
	@GeneratedValue
	private long id;
	private long uid;
	private String cid;
	private int created;
	private int updated;
	private EmployeeState state;

	@Override
	public Long key() {
		return this.id;
	}
}
