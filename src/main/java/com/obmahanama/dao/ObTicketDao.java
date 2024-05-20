package com.obmahanama.dao;

import java.util.List;

import com.obmahanama.dto.GlobalValDto;
import com.obmahanama.dto.TicketDetails;
import com.obmahanama.dto.TicketIndividualDetail;

public interface ObTicketDao {

	List<GlobalValDto> GlobalVal();

	boolean insertTicketDetails(TicketDetails ob);

	boolean insertIndividualTicketDetails(TicketIndividualDetail ob);

	String getLastGroupTicketId();

	int getReservedCount(String type);

	List<TicketDetails> getTicketDetail(String orderId);

	List<String> getTicketIndividualDetail(String orderId, String type);

}
