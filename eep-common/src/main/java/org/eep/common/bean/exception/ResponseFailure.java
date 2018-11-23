package org.eep.common.bean.exception;

public class ResponseFailure extends RuntimeException {

	private static final long serialVersionUID = 9011696017622574471L;
	
	private String msg;
	private String code;
	
	public ResponseFailure() {}
	
	public ResponseFailure(String code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public String msg() {
		return msg;
	}
	
	public String code() {
		return code;
	}
}

