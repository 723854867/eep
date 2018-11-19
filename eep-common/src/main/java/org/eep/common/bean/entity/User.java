package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Identifiable<Long> {

	private static final long serialVersionUID = 9123063173591151725L;

	@Id
	@GeneratedValue
	private long id;
	private String cid;
	private String pwd;
	private Long region;
	private int created;
	private int updated;
	private String salt;
	private String uname;
	private String avatar;
	private String mobile;
	private String cornette;
	private String nickname;
	
	@Override
	public Long key() {
		return this.id;
	}
}
