package com.obmahanama.service;

import com.obmahanama.dto.TicketDetails;

public interface ObMahanamaService {

	boolean insertTicketDetails(TicketDetails ticketDetails);

	TicketDetails getTicketDetails(String orderId);

}
