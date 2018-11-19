package org.eep.bean.param;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.param.Param;
import org.rubik.util.common.DateUtil;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectCreateParam extends Param {

	private static final long serialVersionUID = -8249742022305665269L;

	@Min(1)
	private long time;
	@NotEmpty
	private String cid;
//	@NotEmpty
//	private String rid;
//	@Min(1)
//	private long nextTime;
	private String content;
	private List<MultipartFile> files;
	
	@Override
	public void verify() {
		super.verify();
//		Assert.isTrue(nextTime > DateUtil.current(), Code.PARAM_ERR);
	}
}
