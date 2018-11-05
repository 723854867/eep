package org.eep.sync.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewTsCodeEqusortcode implements Identifiable<String> {

	private static final long serialVersionUID = -6120767304531563356L;
	
	@Id
	private String code;
	private String name;

	@Override
	public String key() {
		return this.code;
	}
}
