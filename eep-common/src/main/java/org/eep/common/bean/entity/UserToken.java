package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;
import org.rubik.bean.core.enums.Client;
import org.rubik.bean.core.enums.Device;
import org.rubik.bean.core.enums.OS;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户设备信息:同一个设备只能登陆一个用户
 * 
 * <pre>
 * device 和 uid 做唯一索引
 * token 做 unique 索引
 * </pre>
 * 
 * @author lynn
 */
@Getter
@Setter
public class UserToken implements Identifiable<String> {

	private static final long serialVersionUID = 4745108999226672474L;

	private OS os;
	private long uid;
	private int created;
	@Id
	private String token;
	private String uname;
	private Client client;
	private Device device;

	@Override
	public String key() {
		return this.token;
	}
}
