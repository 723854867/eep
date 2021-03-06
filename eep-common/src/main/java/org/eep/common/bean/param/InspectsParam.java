package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectsParam extends Param {

	private static final long serialVersionUID = 1057324503120177302L;

	@Null
	private Long min;
	@Null
	private Long max;
	private String cid;
	@Min(1)
	private Long region;
	private String cname;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
