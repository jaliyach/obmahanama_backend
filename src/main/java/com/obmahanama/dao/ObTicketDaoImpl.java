package com.obmahanama.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.obmahanama.dto.GlobalValDto;
import com.obmahanama.dto.TicketDetails;
import com.obmahanama.dto.TicketIndividualDetail;

@Repository
public class ObTicketDaoImpl implements  ObTicketDao{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	
	 public List<Map<String, Object>> getUsers() {
	        String sql = "select * from global_value where use_flag=1";
	        return jdbcTemplate.queryForList(sql);
	    }
	 
	 
	  @Override
	    public List<GlobalValDto> GlobalVal() {
	    
	        List<GlobalValDto> globalValDto = new ArrayList<GlobalValDto>();
	        try {
	            String sql = "select adult_ticket_count,child_ticket_count from global_value where use_flag=1";
	            globalValDto = jdbcTemplate.query(sql, new RowMapper<GlobalValDto>() {

	                public GlobalValDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	                	GlobalValDto Gdto = new GlobalValDto();
	                	Gdto.setAdultTicketCount(rs.getInt(1));
	                	Gdto.setChildTicketCount(rs.getInt(2));
	                    return Gdto;
	                }
	            });

	        } catch (Exception dsa) {
	         
	        }
	        return globalValDto;
	    }
	  
	    @Override
	    public boolean insertTicketDetails(TicketDetails ob) {
	     
	        boolean isSuccess = false;
	      
	        try {
	            int count = 0;
	           
	            Object[] para = new Object[]{ob.getTicket_group(),ob.getMember_name(), ob.getMember_id(), ob.getMember_email(), ob.getMember_mobile(),ob.getStatus_flag(),ob.getAdultCount(),ob.getChildCount()};
	            String sql_insert = "INSERT INTO member_details (ticket_group,member_name,member_id,member_email,member_mobile,status_flag,created_at,adult_count,child_count) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?)";
	            count=jdbcTemplate.update(sql_insert, para);
	            if(count > 0) {
	            isSuccess = true;
	            }
	        } catch (Exception ex) {
	          System.out.println(ex.getMessage());
	          
	          ex.printStackTrace();
	        }
	        
	        return isSuccess;
	      
	    }
	    
	    
	    @Override
	    public boolean insertIndividualTicketDetails(TicketIndividualDetail ob) {
	     
	        boolean isSuccess = false;
	      
	        try {
	            int count = 0;
	           
	            Object[] para = new Object[]{ob.getTicketId(),ob.getTicketGroup(),ob.getTicketType()};
	            String sql_insert = "INSERT INTO ticket_details (ticket_id,ticket_group,created_at,ticket_type) VALUES (?,?,CURRENT_TIMESTAMP,?)";
	            count=jdbcTemplate.update(sql_insert, para);
	            if(count > 0) {
	            isSuccess = true;
	            }
	        } catch (Exception ex) {
	          
	        }
	        
	        return isSuccess;
	      
	    }
	    
	    
	    @Override
	    public String getLastGroupTicketId() {
	    
	       String ticketId="na";
	        try {
	            String sql = "SELECT  ticket_group FROM mahanamaob.member_details order by id desc LIMIT 1";
	            ticketId = jdbcTemplate.queryForObject(sql, String.class);

	        } catch (Exception dsa) {
	         
	        }
	        return ticketId;
	    }
	    
	    
	    @Override
	    public int getReservedCount(String type) {
	    
	       int ticketCount=0;
	        try {
	            String sql = "SELECT  count(ticket_group) FROM mahanamaob.ticket_details where ticket_type='"+type+"' ";
	            ticketCount = jdbcTemplate.queryForObject(sql, Integer.class);

	        } catch (Exception dsa) {
	         
	        }
	        return ticketCount;
	    }
	    
	    
		    @Override
		    public List<TicketDetails> getTicketDetail(String orderId) {
		    
		        List<TicketDetails> ticketDto = new ArrayList<TicketDetails>();
		        try {
		            String sql = "SELECT  ticket_group,member_name,member_id,member_email,member_mobile,adult_count,child_count FROM mahanamaob.member_details where ticket_group='"+orderId+"' ";
		            ticketDto = jdbcTemplate.query(sql, new RowMapper<TicketDetails>() {

		                public TicketDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		                	TicketDetails Gdto = new TicketDetails();
		                	Gdto.setTicket_group(rs.getString(1));
		                	Gdto.setMember_name(rs.getString(2));
		                	Gdto.setMember_id(rs.getString(3));
		                	Gdto.setMember_email(rs.getString(4));
		                	Gdto.setMember_mobile(rs.getString(5));
		                	Gdto.setAdultCount(rs.getInt(6));
		                	Gdto.setChildCount(rs.getInt(7));
		                    return Gdto;
		                }
		            });

		        } catch (Exception dsa) {
		         
		        }
		        return ticketDto;
		    }
		    
		    
		    @Override
		    public List<String> getTicketIndividualDetail(String orderId,String type) {
		    
		        List<String> ticketDto = new ArrayList<String>();
		        try {
		            String sql = "SELECT ticket_id FROM mahanamaob.ticket_details where ticket_group='"+orderId+"' and ticket_type='"+type+"'";
		            ticketDto = jdbcTemplate.query(sql, new RowMapper<String>() {

		                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		                	
		                    return rs.getString(1);
		                }
		            });

		        } catch (Exception dsa) {
		         
		        }
		        return ticketDto;
		    }
	
}
