package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.eep.common.bean.enums.WarnLevel;
import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.param.Param;
import org.rubik.util.common.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RectifyNoticeCreateParam extends Param {

	private static final long serialVersionUID = 5001525730057917593L;

	@NotEmpty
	private String cid;
	@NotEmpty
	private String problem;
	@NotEmpty
	private String measure;
	private int closingTime;
	private boolean smsSend;
	@NotEmpty
	private String deregulation;
	@NotEmpty
	private String processBasis;
	@NotNull
	private WarnLevel warnLevel;
	
	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(closingTime > DateUtil.current(), Code.PARAM_ERR);
		Assert.isTrue(warnLevel == WarnLevel.RED || warnLevel == WarnLevel.BLUE, Code.PARAM_ERR);
	}
}
