package org.eep.common.bean.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawCreateParam extends Param {

	private static final long serialVersionUID = -1580488497729885593L;

	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	@Min(1)
	private int categoryId;
}
