package org.eep.common.bean.param;

import org.rubik.bean.core.param.SidParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyModifyParam extends SidParam {

	private static final long serialVersionUID = 8214126943235865939L;

	private String memo;
}
