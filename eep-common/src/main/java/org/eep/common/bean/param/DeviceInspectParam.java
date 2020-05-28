package org.eep.common.bean.param;

import org.rubik.bean.core.Assert;
import org.rubik.bean.core.model.Code;
import org.rubik.bean.core.param.Param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceInspectParam extends Param{

	private static final long serialVersionUID = -7009255658399848047L;
	
	//设备名称
	private String name;
	//检查人员
	private String username;
	//检查时间
	private Integer start;
	private Integer end;
	
	@Override
	public void verify() {
		if(start != null && end != null)
			Assert.isTrue(end >= start, Code.PARAM_ERR,"结束时间必须大于开始时间");
		super.verify();
	}

}
