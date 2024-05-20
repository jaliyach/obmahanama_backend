package com.obmahanama.dto;

import java.util.List;

import lombok.Data;

@Data
public class TicketDetails {
	
	
String ticket_group;
String member_name;
String member_id;
String member_email;
String member_mobile;
String status_flag;
int adultCount;
int childCount;

List<String> ticketChildId;
List<String> ticketAdultId;
}
