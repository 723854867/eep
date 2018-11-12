package org.eep.common.bean.param;

import javax.validation.constraints.Min;

import org.rubik.bean.core.param.IdParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawModifyParam extends IdParam {

	private static final long serialVersionUID = 1218089307166267750L;

	private String title;
	private String content;
	@Min(1)
	private Integer categoryId;
}
