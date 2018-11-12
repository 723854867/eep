package org.eep.sync.bean.entity;

import java.util.Date;

import javax.persistence.Id;

import org.rubik.bean.core.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewTsPeroperatorcert implements Identifiable<String> {

	private static final long serialVersionUID = 4647141188907405289L;

	@Id
	private String sid;
	// 作业人员编号
	private String sidBaseperoperator;
	// 取资质类型
	private String applycategory;
	// 作业种类
	private String worktype;
	// 作业级别
	private String worklevel;
	// 作业项目
	private String workitem;
	// 批准项目明细
	private String authworkitem;
	// 考核机构
	private String exaorgname;
	// 发证机构
	private String isscerorgname;
	// 批准日期
	private Date pasdate;
	// 有效日期
	private Date expdate;
	// 学号
	private String studentno;
	// 持证状态
	private String state;
	private Date updatedate;
	
	@Override
	public String key() {
		return this.sid;
	}
}
