package com.rigil.meetingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rigil.meetingapp.WMRUtil;

/**
 * This servlet responds to the request corresponding to meetings. The servlet
 * manages the Meeting Entity
 * 
 * @author
 */

@SuppressWarnings("serial")
public class WMeetingReportServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(MeetingServlet.class.getCanonicalName());

  /**
   * Get the entities in JSON format.
  */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    super.doGet(req, resp);
    logger.log(Level.INFO, "Obtaining Meeting listing");
    
    
    
    
	
	
    String searchFor = req.getParameter("query");
    
    PrintWriter out = resp.getWriter(); 
    PrintWriter setout2;
    Iterable<Entity> entities = null;
    String uri = req.getRequestURI();
 	logger.log(Level.INFO,""+uri  +"  ");
    if (searchFor != null) {
    	
    	Entity e = WMeetingReport.getMeeting(searchFor);
    	if (e != null) {
	        Set<Entity> result = new HashSet<Entity>();
	        result.add(e);
	        out.println(WMRUtil.writeReportEntityJSON(e, "Agendaitem", "Note", "Actionitem", "Attachment", "agimeetingID", "nparentID",	"aciparentID", "aparentID"));
	        }
    	else {
    		out.println("[]");    		
    	}
    	logger.log(Level.INFO, "Is ERROR? " + out.checkError() + "  ");
	        
    	
    	
	      
    	
    	
    	
    	
    	
	        


    	
    	
    	
    	
    			

    	
    	
	        
	        
	        
    
    	/*
      entities = Meeting.getAllMeetings("Meeting");
	      out.println(MRUtil.writeJSON(entities, "Agendaitem", "meetingID"));
     */
      /*
      Entity meeting = Meeting.getMeeting(meetingID);
      key meetingkey = meeting.getKey();
      out.println(Util.listChildren("Agendaitem", meetingkey));
	    */
    	/*
    	entities = Agendaitem.getAgendaitemsForMeeting("Agendaitem", "meetingID");
        out.println(Util.writeJSON(entities, "Agendaitem", "meetingID"));
        */
    } else {
  /*
	      Entity e = Meeting.getMeeting(searchFor);
    		
    		
	      if (e != null) {
		        Set<Entity> result = new HashSet<Entity>();
		        result.add(e);
		     
		        
		        
		        out.println(MRUtil.writeEntityJSON(e, "Agendaitem", "meetingID"));
		        
		      
	     }
    */}
    
  }
/*
  if (searchFor == null || searchFor.equals("")) {
      entities = Order.getAllOrders();
      out.println(Util.writeJSON(entities, "LineItem", "orderID"));
    } else {
      entities = Order.getAllOrdersForCustomer(searchFor);
      out.println(Util.writeJSON(entities, "LineItem", "orderID"));
    }
  */
	
}