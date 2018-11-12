package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawCategory implements Identifiable<Integer> {

	private static final long serialVersionUID = 3466045377914541930L;

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int created;
	private int updated;
	
	@Override
	public Integer key() {
		return this.id;
	}
}
