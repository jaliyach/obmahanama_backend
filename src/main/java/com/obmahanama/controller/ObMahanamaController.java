package com.obmahanama.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.obmahanama.dao.ObTicketDao;
import com.obmahanama.dto.GlobalValDto;
import com.obmahanama.dto.MainResponse;
import com.obmahanama.dto.ResponseTicket;
import com.obmahanama.dto.TicketDetails;
import com.obmahanama.dto.TransactionDetailRequest;
import com.obmahanama.service.ObMahanamaService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/main")
public class ObMahanamaController {

	@Autowired
	ObTicketDao obTicketDao;

	@Autowired
	ObMahanamaService obMahanamaService;

	private static final Logger log = Logger.getLogger(ObMahanamaController.class);

	@PostMapping("/globalVal")
	public MainResponse globalValues() {

		MainResponse resOb = new MainResponse();
		resOb.setResponseCode("0000");
		resOb.setResponseDescription("System Error");

		try {

			List<GlobalValDto> globalValDto = obTicketDao.GlobalVal();
			if (globalValDto.size() > 0) {
				GlobalValDto ob = globalValDto.get(0);
				ob.setIssuedAdultCount(obTicketDao.getReservedCount("A"));
				ob.setIssuedChildCount(obTicketDao.getReservedCount("C"));
				resOb.setResponseCode("1111");
				resOb.setResponseDescription("Success Transaction");
				resOb.setGlobalValDto(ob);

			}

		} catch (Exception e) {

		}

		return resOb;

	}

	@PostMapping("/insertTicketDetails")
	public MainResponse insertTicketDetails(@RequestBody TicketDetails ticketDetails) {

		MainResponse resOb = new MainResponse();
		resOb.setResponseCode("0000");
		resOb.setResponseDescription("System Error");
		boolean isSuccess = false;
		try {
			 Gson gson = new Gson();
             String jsonString = gson.toJson(ticketDetails);
             
             System.out.println(jsonString);
             
             //--------generate child and adult individual ticktes 
             
           
             
             
			isSuccess=obMahanamaService.insertTicketDetails(ticketDetails);
			
			if(isSuccess) {
				
				resOb.setResponseCode("1111");
				resOb.setResponseDescription("Success Transaction");
			}

		} catch (Exception e) {

		}

		return resOb;

	}

	@PostMapping("/getLastTicketGroupId")
	public MainResponse getLastTicketGroupId() {

		MainResponse resOb = new MainResponse();
		resOb.setResponseCode("0000");
		resOb.setResponseDescription("System Error");

		try {

			String ticketGroupId = obTicketDao.getLastGroupTicketId();
			if (ticketGroupId.equals("na")) {

				resOb.setResponseCode("1001");
				resOb.setResponseDescription("empty result");

			} else {

				resOb.setResponseCode("1111");
				resOb.setResponseDescription(ticketGroupId);
			}

		} catch (Exception e) {

		}

		return resOb;

	}
	
	
	@PostMapping("/getTransactionDetails")
	public ResponseTicket getTransactionDetails(@RequestBody TransactionDetailRequest transactionDetailRequest) {
		ResponseTicket resOb = new ResponseTicket();
		resOb.setResponseCode("0000");
		resOb.setResponseDescription("System Error");

		try {

			TicketDetails detail=obMahanamaService.getTicketDetails(transactionDetailRequest.getOrderId());
			if (detail==null) {

				resOb.setResponseCode("1001");
				resOb.setResponseDescription("empty result");

			} else {

				resOb.setResponseCode("1111");
				resOb.setResponseDescription("Success");
				resOb.setDetail(detail);
			}

		} catch (Exception e) {

		}
		
		return resOb;
		
	}
	

}
