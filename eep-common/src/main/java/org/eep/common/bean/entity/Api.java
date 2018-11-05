package org.eep.common.bean.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lynn
 */
@Getter
@Setter
public class Api implements Identifiable<Integer> {

	private static final long serialVersionUID = 1457653518786212864L;
	
	@Id
	@GeneratedValue
	private int id;
	private int created;
	private int updated;
	private String path;
	private String desc;
	// 是否启用用户分布式锁：同一用户 (User) 同一时间只能请求一个 lock 为 true 的接口
	private boolean lock;
	// 是否需要登录
	private boolean login;
	// 是否商户接口
	private boolean tenant;
	// 是否记录访问日志
	private boolean logged;
	// 用户分布式锁锁定时间：超过该时间还没有解锁则用户获取的分布式锁会自动解锁,单位毫秒
	private int lockExpire;
	// 获取用户分布式锁超时时间：超过该时间还没获取到用户分布式锁则返回失败,单位毫秒
	private int lockTimeout;
	// 是否检查ip白名单
	private boolean ipFilter;
	// 接口安全等级(该参数一般用在不支持热部署的环境中，因为不支持热部署的服务器需要停服这段时间无法访问因此要平滑的关闭服务器就要将服务器的安全等级先设置大于该值一段时间后再关闭服务器)
	private int securityLevel;
	// 是否参与权限管理
	private boolean checkAuth;

	@Override
	public Integer key() {
		return this.id;
	}
}
