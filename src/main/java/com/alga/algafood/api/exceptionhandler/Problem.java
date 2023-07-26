package com.alga.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;

}
