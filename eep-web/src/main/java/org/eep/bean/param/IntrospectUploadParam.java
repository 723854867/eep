package org.eep.bean.param;

import java.util.List;

import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.param.LidParam;
import org.rubik.util.common.CollectionUtil;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntrospectUploadParam extends LidParam {

	private static final long serialVersionUID = 7945722927928984718L;

	private String content;
	private List<MultipartFile> files;
	
	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(!CollectionUtil.isEmpty(files), Code.PARAM_ERR, "files is empty");
	}
}
