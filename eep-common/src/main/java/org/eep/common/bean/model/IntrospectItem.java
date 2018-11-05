package org.eep.common.bean.model;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class IntrospectItem implements Serializable {

	private static final long serialVersionUID = -8695989996512485178L;

	@NotEmpty
	@Size(max = 20)
	private Map<String, Integer> details;
}
