package org.eep.common.bean.param;

import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.param.IdParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LawCategoryModifyParam extends IdParam {

	private static final long serialVersionUID = 4459560130468997189L;
	
	@NotEmpty
	private String name;
}
