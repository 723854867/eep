package org.eep.chuanglan.model;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VarSmsResponse extends ChuangLanResponse {

	private static final long serialVersionUID = -2037703879908592938L;

	// 失败条数
	@Expose
	private int failNum;
	@Expose
	private String msgId;
	// 成功条数
	@Expose
	private int successNum;
}
