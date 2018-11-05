package org.eep.sync.bean.entity;

import java.util.Date;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewTsEquinspect implements Identifiable<String> {

	private static final long serialVersionUID = -2404071186218409905L;
	
	@Id
	private String sid;
	// 检验单位名称
	private String cenname;
	// 设备边哈
	private String sidBaseequmain;
	// 检验人员
	private String insusername;
	// 检验结论
	private String insverdict;
	// 主要问题
	private String majquestion;
	// 检验日期
	private Date insdate;
	// 下次检验日期
	private Date nextinsdate;
	// 报告编号
	private String repno;
	// 更新时间
	private Date updatedate;
	// 检验类型
	private String instypecode;

	@Override
	public String key() {
		return this.sid;
	}
}
