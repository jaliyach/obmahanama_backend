package com.obmahanama.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.obmahanama.dao.ObTicketDao;
import com.obmahanama.dto.MainResponse;
import com.obmahanama.dto.TicketDetails;
import com.obmahanama.dto.TicketIndividualDetail;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ObMahanamaServiceImpl implements ObMahanamaService {

	@Autowired
	ObTicketDao obTicketDao;
	
	  @Autowired
	    private JavaMailSender mailSender;

	@Override
	public boolean insertTicketDetails(TicketDetails ticketDetails) {
		boolean isSuccess = false;
		try {

			String ticketPrefix = "MOB00";
			String ticketGroupId = null;

			String lastTicketGroupId = obTicketDao.getLastGroupTicketId();
			if (lastTicketGroupId.equals("na")) {

				ticketGroupId = ticketPrefix + "1";

			} else {

				String suffixLastId = lastTicketGroupId.substring(4, lastTicketGroupId.length());
				int suffixLastInt = Integer.parseInt(suffixLastId) + 1;

				ticketGroupId = ticketPrefix + String.valueOf(suffixLastInt);

			}
			ticketDetails.setTicket_group(ticketGroupId);
			isSuccess = obTicketDao.insertTicketDetails(ticketDetails);
			
			//----------------
			  int adutCount =ticketDetails.getAdultCount();
	          int childCount =ticketDetails.getChildCount();
	          
	         for(int i = 0;adutCount>i ; i++) { 
	        	 int j=i+1;
	        	 TicketIndividualDetail  obs=new TicketIndividualDetail();
	        	 obs.setTicketGroup(ticketGroupId);
	        	 obs.setTicketId("5001"+ticketGroupId+String.valueOf(j));
	        	 obs.setTicketType("A");
	             obTicketDao.insertIndividualTicketDetails(obs);
	          
	         }
	         
	         
	         for(int i = 0; childCount>i ; i++) { 
	        	 int j=i+1;
	        	 TicketIndividualDetail  obs=new TicketIndividualDetail();
	        	 obs.setTicketGroup(ticketGroupId);
	        	 obs.setTicketId("7001"+ticketGroupId+"C"+String.valueOf(j));
	        	 obs.setTicketType("C");
	             obTicketDao.insertIndividualTicketDetails(obs);
	          
	         }
	         
	         
			 String text="<p>Dear Sir,</p><p><br></p>\r\n"
			 		+ "<p>Your reservation was successfully updated. We will gladly invite you to the to the event .</p>\r\n"
			 		+ "<p>Your reservation as follows&nbsp;</p>\r\n"
			 		+ "<p>Adult Ticket IDs</p> " 
			 		+ "<p>* MOB001A1</p>\r\n"
			 		+ "<p>* MOB001A2</p>\r\n"
			 		+ "<p>Child Ticket IDs</p>\r\n"
			 		+ "<p>* MOB001C1</p>\r\n"
			 		
			 		+ "<p><br></p>\r\n"
			 		+ "<p>Thank you</p>\r\n"
			 		+ "<p>Organizing Commitee</p>";
			 String heading="Mahanama OB UK Dinner Dance Ticket Reserves :-"+ticketGroupId;
			 sendHtmlEmail(ticketDetails.getMember_email(), heading, text);

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

			ex.printStackTrace();
		}

		return isSuccess;

	}
	
	
  
	  public void sendHtmlEmail(String to, String subject, String htmlContent)  {
		  try {
		    MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(htmlContent, true);
	        helper.setFrom("mahanamaoldboyuk@gmail.com");
	        mailSender.send(message);
	        
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       
	    }
	  
	  @Override
	  public TicketDetails getTicketDetails(String orderId) {
		  TicketDetails ob=new TicketDetails();
		  
		  List<TicketDetails> ticketList=obTicketDao.getTicketDetail(orderId);
		  ob=ticketList.get(0);
		  ob.setTicketAdultId(obTicketDao.getTicketIndividualDetail(orderId,"A"));
		  ob.setTicketChildId(obTicketDao.getTicketIndividualDetail(orderId,"C"));
		  
		  
		  return ob;
	  }


	public static String generateTimeId() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(ZoneId.systemDefault());
		return formatter.format(Instant.now());
	}

}
