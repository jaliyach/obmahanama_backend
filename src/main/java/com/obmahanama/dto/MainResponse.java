package com.obmahanama.dto;

import lombok.Data;

@Data
public class MainResponse {
	
	
	String responseCode;
	String responseDescription;
	
	GlobalValDto globalValDto;

}
