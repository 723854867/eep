package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.eep.common.bean.enums.AlertType;
import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertsParam extends Param {

	private static final long serialVersionUID = 1229960663520315753L;

	private Long id;
	@Null
	private Long min;
	@Null
	private Long max;
	private String cid;
	@Min(1)
	private Long region;
	private String cname;
	private String dname;
	private AlertType type;
	private Long rectifyId;
	private String deviceId;
	private WarnLevel warnLevel;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
