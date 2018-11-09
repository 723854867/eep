package org.eep.common.bean.model;

import java.io.Serializable;

import org.eep.common.bean.enums.WarnLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertStatistic implements Serializable {

	private static final long serialVersionUID = -8894528992150357834L;

	private int num;
	private WarnLevel warnLevel;
}
