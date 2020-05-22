package org.eep.bean.param;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrcodeDownloadParam implements Serializable{
	
	private static final long serialVersionUID = 5964831573703514749L;
	
	@NotNull
	@Size(min=1)
	private Set<String> ids;

}
