package org.carranza.java.msvc.veterinaria.msvcgestioncliente.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageCustom {

	private Object data;	
	private Integer totalPages;

}
