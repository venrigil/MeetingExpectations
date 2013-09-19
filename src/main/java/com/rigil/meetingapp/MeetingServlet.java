/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rigil.meetingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rigil.meetingapp.Util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This servlet responds to the request corresponding to meetings. The servlet
 * manages the Meeting Entity
 * 
 * @author
 */
@SuppressWarnings("serial")
public class MeetingServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(MeetingServlet.class.getCanonicalName());

  /**
   * Get the entities in JSON format.
  */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    super.doGet(req, resp);
    logger.log(Level.INFO, "Obtaining Meeting listing");
    String searchFor = req.getParameter("q");
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    if (searchFor == null || searchFor.equals("") || searchFor == "*") {
    	
      entities = Meeting.getAllMeetings("Meeting");
	      out.println(Util.writeJSON(entities, "Agendaitem", "agimeetingID"));
     
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
  
	      Entity e = Meeting.getMeeting(searchFor);
    		
    		
	      if (e != null) {
		        Set<Entity> result = new HashSet<Entity>();
		        result.add(e);
		     
		        
		        
		        out.println(Util.writeEntityJSON(e, "Agendaitem", "agimeetingID"));
		        
		      
	     }
    }
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
	/**
	 * Create the entity and persist it.
	 */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	    logger.log(Level.INFO, "Creating Meeting");
    PrintWriter out = resp.getWriter();

    try {
        SimpleDateFormat msDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
        String mtitle = req.getParameter("mtitle");
        String mdesc = req.getParameter("mdesc");
        String mlocation = req.getParameter("mlocation");
        String meetingID = req.getParameter("meetingID");
        String mid = req.getParameter("mid");
        
        String mdatestr = req.getParameter("mdate");
        Date mdate = msDateFormat.parse(mdatestr);

        Integer mplannedDuration = Integer.parseInt(req.getParameter("mplannedDuration"));
        
        String mplannedStartDateTimestr = req.getParameter("mplannedStartDateTime");
        Date mplannedStartDateTime = msDateFormat.parse(mplannedStartDateTimestr);

        Integer mplannedStart = Integer.parseInt(req.getParameter("mplannedStart"));
        
        String mplannedEndDateTimestr = req.getParameter("mplannedEndDateTime");
        Date mplannedEndDateTime = msDateFormat.parse(mplannedEndDateTimestr);
        
        Integer mplannedEnd = Integer.parseInt(req.getParameter("mplannedEnd"));
       
        String msyncDatestr = req.getParameter("msyncDate");
        Date msyncDate = msDateFormat.parse(msyncDatestr);
       
        String mcreatedOnstr = req.getParameter("mcreatedOn");
        Date mcreatedOn = msDateFormat.parse(mcreatedOnstr);
       
        Integer mactualDuration = Integer.parseInt(req.getParameter("mactualDuration"));
        String mcreatedBy = req.getParameter("mcreatedBy");
        Boolean mdeleted = Boolean.valueOf(req.getParameter("mdeleted"));
        
        String mmodifiedOnstr = req.getParameter("mmodifiedOn");
        Date mmodifiedOn = msDateFormat.parse(mmodifiedOnstr);
        
        String magendaitemsString = req.getParameter("magendaitemsString");
        String mparticipantsString = req.getParameter("mparticipantsString");
        String murl = req.getParameter("murl");
        String mmeetings = req.getParameter("mmeetings");
        String magendaItems = req.getParameter("magendaItems");
        String mparticipants = req.getParameter("mparticipants");
        String mnotes = req.getParameter("mnotes");
        String mactionItems = req.getParameter("mactionItems");
        String mWebCreated = req.getParameter("mWebCreated");
        String miPadAppCreated = req.getParameter("miPadAppCreated");
        String mAndroidCreated = req.getParameter("mAndroidCreated");
        String mOtherCreated = req.getParameter("mOtherCreated");
        String mAddlColumn1 = req.getParameter("mAddlColumn1");
        String mAddlColumn2 = req.getParameter("mAddlColumn2");
        String mAddlColumn3 = req.getParameter("mAddlColumn3");
        String mAddlColumn4 = req.getParameter("mAddlColumn4");
        String mAddlColumn5 = req.getParameter("mAddlColumn5");
        
        Meeting.createOrUpdateMeeting(mtitle, mdesc, mlocation, meetingID, mid, mdate, mplannedDuration, mplannedStartDateTime,
       		 mplannedStart, mplannedEndDateTime, mplannedEnd, msyncDate, mcreatedOn, mactualDuration, mcreatedBy, mdeleted, 
       		 mmodifiedOn, magendaitemsString, mparticipantsString, murl, mmeetings, magendaItems, mparticipants, mnotes, mactionItems, 
       		 mWebCreated, miPadAppCreated, mAndroidCreated, mOtherCreated, mAddlColumn1, mAddlColumn2, mAddlColumn3, mAddlColumn4,
       		 mAddlColumn5);
        
    } catch (ParseException e) {
        e.printStackTrace();
    } catch (Exception e) {
      String msg = Util.getErrorResponse(e);
      out.print(msg);
    }
  }

	/**
	 * Delete the meeting. Gives an error when we try to delete the meeting that has items
	 * associated with it
	 */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
    logger.log(Level.INFO, "Deleting the Meeting");
    String meetingkey = req.getParameter("id");
    Key key = KeyFactory.createKey("Meeting", meetingkey);
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = Util.listEntities("Item", "meeting", meetingkey);
    for (Entity e : entities) {
      if (e != null)
		        out.println("Cannot delete meeting as there are items associated with this product.");
	      return;
    }
	    try {
	      Util.deleteFromCache(key);
	      Util.deleteEntity(key);
	    } catch (Exception e) {
	      String msg = Util.getErrorResponse(e);
	      out.print(msg);
    }
  }

	/**
	 * Redirect the call to doDelete or doPut method
	 */
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    String action = req.getParameter("action");
    if (action.equalsIgnoreCase("delete")) {
      doDelete(req, resp);
	      return;
    } else if (action.equalsIgnoreCase("put")) {
	      doPut(req, resp);
      return;
    }
  }
}