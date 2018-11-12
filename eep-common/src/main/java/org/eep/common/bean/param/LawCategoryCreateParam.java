package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawCategoryCreateParam extends Param {

	private static final long serialVersionUID = 1767664590307129121L;

	@NotEmpty
	private String name;
}
