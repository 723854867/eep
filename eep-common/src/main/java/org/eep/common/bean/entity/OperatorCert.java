package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.eep.common.bean.enums.AuditType;
import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorCert implements Identifiable<String> {

	private static final long serialVersionUID = -7207795380947410405L;
	
	@Id
	private String id;
	private String cid;
	// 作业种类
	private String type;
	private int updated;
	// 作业级别
	private String level;
	// 批准项目明细
	private String items;
	// 持证状态
	private String state;
	// 学号(证书编号)
	private String certno;
	// 有效日期
	private int expireTime;
	// 批准日期
	private int approvalTime;
	// 作业人员编号
	private String operatorId;
	// 考核机构
	private String examAgency;
	// 发证机构
	private String issueAgency;
	// 资质类型
	private AuditType auditType;

	@Override
	public String key() {
		return this.id;
	}
}
