package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysRegion implements Identifiable<Long> {

	private static final long serialVersionUID = -4479534054579004710L;

	@Id
	private long id;
	private int left;
	private int right;
	private int layer;
	private String code;
	private String name;
	private int created;
	private int updated;
	private boolean open;
	private String trunk;
	
	@Override
	public Long key() {
		return this.id;
	}
}
