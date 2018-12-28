package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyCustom implements Identifiable<String> {

	private static final long serialVersionUID = -815290386109463908L;
	
	@Id
	private String id;
	private String memo;

	@Override
	public String key() {
		return this.id;
	}
}
