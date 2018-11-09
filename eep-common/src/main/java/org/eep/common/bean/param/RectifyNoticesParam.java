package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import org.eep.common.bean.enums.RectifyState;
import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RectifyNoticesParam extends Param {

	private static final long serialVersionUID = -7070845157631947161L;

	@Min(1)
	private Long id;
	@Null
	private Long min;
	@Null
	private Long max;
	private String cid;
	@Min(1)
	private Long region;
	private String cname;
	@Min(1)
	private Long committer;
	private RectifyState state;
	private WarnLevel warnLevel;
	
	@Override
	public void verify() {
		super.verify();
		pageCheck();
	}
}
