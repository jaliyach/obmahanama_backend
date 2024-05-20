package com.obmahanama.dto;

import lombok.Data;

@Data
public class ResponseTicket {

	String responseCode;
	String responseDescription;
	
	TicketDetails detail;
	
}
