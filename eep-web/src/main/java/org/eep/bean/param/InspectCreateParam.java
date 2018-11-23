package org.eep.bean.param;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.rubik.bean.core.param.Param;
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
	private String content;
	private boolean smsSend;
	private List<MultipartFile> files;
	
	@Override
	public void verify() {
		super.verify();
	}
}
