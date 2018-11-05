package org.eep.common.bean.entity;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogExamine implements Identifiable<String> {

	private static final long serialVersionUID = 8749619996580755004L;
	
	@Id
	// 日志编号
	private String id;
	// 检验类型
	private String type;
	// 更新时间
	private int updated;
	// 检验日期
	private int created;
	// 下次检验日期
	private long nextTime;
	// 报告编号
	private String repNo;
	// 设备编号
	private String deviceId;
	// 检验人员
	private String examiner;
	// 主要问题
	private String problems;
	// 检验结论
	private String conclusion;
	// 检验单位
	private String inspectionAgency;

	@Override
	public String key() {
		return this.id;
	}
}
