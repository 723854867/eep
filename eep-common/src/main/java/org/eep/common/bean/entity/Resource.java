package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.eep.common.bean.enums.ResourceType;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Resource implements Identifiable<Long> {

	private static final long serialVersionUID = -955424725585202430L;
	
	@Id
	@GeneratedValue
	private long id;
	private long bytes;
	private String url;
	private String name;
	private String path;
	private int created;
	private String owner;
	private int priority;
	private ResourceType type;

	@Override
	public Long key() {
		return this.id;
	}
}
