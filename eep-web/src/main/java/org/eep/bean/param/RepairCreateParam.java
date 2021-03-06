package org.eep.bean.param;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.param.Param;
import org.rubik.util.common.CollectionUtil;
import org.rubik.util.common.DateUtil;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairCreateParam extends Param {

	private static final long serialVersionUID = -347009764418227057L;

	// 使用单位编号
	private String cid;
	// 下次维保时间
	private int nextTime;
	private String content;
	private Set<String> devices;
	@DecimalMax("90")
	@DecimalMin("-90")
	private BigDecimal latitude;
	@DecimalMax("180")
	@DecimalMin("-180")
	private BigDecimal longitude;
	private List<MultipartFile> files;
	
	@Override
	public void verify() {
		super.verify();
		Assert.hasText(cid, Code.PARAM_ERR, "cid miss");
		Assert.isTrue(!CollectionUtil.isEmpty(files), Code.PARAM_ERR, "files is empty");
		Assert.isTrue(!CollectionUtil.isEmpty(devices), Code.PARAM_ERR, "devices is empty");
		Assert.isTrue(nextTime > DateUtil.current(), "next inspect time must large than current time");
		if (null == latitude)
			this.latitude = BigDecimal.ZERO;
		if (null == longitude)
			this.longitude = BigDecimal.ZERO;
		this.latitude = this.latitude.setScale(7, RoundingMode.HALF_UP);
		this.longitude = this.longitude.setScale(7, RoundingMode.HALF_UP);
	}
}
