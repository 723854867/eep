package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Law implements Identifiable<Integer> {

	private static final long serialVersionUID = 4939196890387212296L;

	@Id
	@GeneratedValue
	private int id;
	private int created;
	private int updated;
	private String title;
	private String content;
	private int categoryId;
	
	@Override
	public Integer key() {
		return this.id;
	}
}
