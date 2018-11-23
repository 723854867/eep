package org.eep.chuanglan.model;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsResponse extends ChuangLanResponse {

	private static final long serialVersionUID = -4348772858083279287L;
	
	@Expose
	private String msgId;
}
