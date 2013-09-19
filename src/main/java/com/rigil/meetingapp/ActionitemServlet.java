package com.rigil.meetingapp;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class ActionitemServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(ActionitemServlet.class.getCanonicalName());

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	super.doGet(req,resp);
	logger.log(Level.INFO, "Obtaining Actionitem listing");
	String searchBy = req.getParameter("actionitem-searchby");
	String searchFor = req.getParameter("query");
	PrintWriter out = resp.getWriter();
	if (searchFor == null || searchFor.equals("")) {
	  Iterable<Entity> entities = Actionitem.getAllActionitems();
	  out.println(Util.writeJSON(entities));
	} else if (searchBy == null || searchBy.equals("actionItemID")) {
	  Iterable<Entity> entities = Actionitem.getActionitem(searchFor);
	  out.println(Util.writeJSON(entities));
	} else if (searchBy != null && searchBy.equals("meeting")) {
	  Iterable<Entity> entities = Actionitem.getActionitemsForMeeting("Actionitem", searchFor);
	  out.println(Util.writeJSON(entities));
	}
  }

  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	logger.log(Level.INFO, "Creating Actionitem");
	try {
	SimpleDateFormat acisDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
	String actionItemID = req.getParameter("actionItemID");
	String aciassignee = req.getParameter("aciassignee");
	String acidesc = req.getParameter("acidesc");
	Boolean acideleted = Boolean.valueOf(req.getParameter("acideleted"));
	String acimeetingID = req.getParameter("acimeetingID");
	
	String acicreatedOnstr = req.getParameter("acicreatedOn");
	Date acicreatedOn = acisDateFormat.parse(acicreatedOnstr);
	
	String acimodifiedOnstr = req.getParameter("acimodifiedOn");
	Date acimodifiedOn = acisDateFormat.parse(acimodifiedOnstr);
	
	String acisyncDatestr = req.getParameter("acisyncDate");
	Date acisyncDate = acisDateFormat.parse(acisyncDatestr);
	
	String acistatus = req.getParameter("acistatus");
	Integer aciorder = Integer.parseInt(req.getParameter("aciorder"));
	
	String acidueDatestr = req.getParameter("acidueDate");
	Date acidueDate = acisDateFormat.parse(acidueDatestr);
	
	String acimeetings = req.getParameter("acimeetings");
	String aciagendaItems = req.getParameter("aciagendaItems");
	String aciparticipants = req.getParameter("aciparticipants");
	String acinotes = req.getParameter("acinotes");
	String aciactionItems = req.getParameter("aciactionItems");
	String aciWebCreated = req.getParameter("aciWebCreated");
	String aciiPadAppCreated = req.getParameter("aciiPadAppCreated");
	String aciAndroidCreated = req.getParameter("aciAndroidCreated");
	String aciOtherCreated = req.getParameter("aciOtherCreated");
	String aciAddlColumn1 = req.getParameter("aciAddlColumn1");
	String aciAddlColumn2 = req.getParameter("aciAddlColumn2");
	String aciAddlColumn3 = req.getParameter("aciAddlColumn3");
	String aciAddlColumn4 = req.getParameter("aciAddlColumn4");
	String aciAddlColumn5 = req.getParameter("aciAddlColumn5");
	String acicreatedBy = req.getParameter("acicreatedBy");
	String aciparentType = req.getParameter("aciparentType");
	String aciparentID = req.getParameter("aciparentID");
	
	Actionitem.createOrUpdateActionitem(actionItemID, aciassignee, acidesc,  acideleted, acimeetingID, acicreatedOn, acimodifiedOn, 
			acisyncDate, acistatus, aciorder, acidueDate, acimeetings, 
			aciagendaItems, aciparticipants, acinotes, aciactionItems, 
			aciWebCreated, aciiPadAppCreated, aciAndroidCreated, aciOtherCreated, aciAddlColumn1, 
			aciAddlColumn2, aciAddlColumn3, aciAddlColumn4, aciAddlColumn5, acicreatedBy, aciparentType, aciparentID);
	
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
			}

  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	super.doGet(req, resp);
	logger.log(Level.INFO, "Deleting Actionitem");
	String actionitemKey = req.getParameter("ActionitemID");
	PrintWriter out = resp.getWriter();
	Iterable<Entity> entities = Util.listEntities("Actionitem", "ActionitemID", actionitemKey);
	try {
	  for (Entity e : entities) {
		if (e != null)
		  out.print("Cannot delete Actionitem as there are Actionitems associated with this Actionitem.");
		return;
	  }
	  Entity e = Actionitem.getSingleActionitem(actionitemKey);
	  Util.deleteFromCache(e.getKey());
	  Util.deleteEntity(e.getKey());
	  out.print("Actionitem deleted successfully.");
	} catch (Exception e) {
	  String msg = Util.getErrorResponse(e);
	  out.print(msg);
	}
  }

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
  
  
  
  /**
   * Format a time from a given format to given target format
   * 
   * @param inputFormat
   * @param inputTimeStamp
   * @param outputFormat
   * @return
   * @throws ParseException
   */
  private static String TimeStampConverter(final String inputFormat,
		  String inputTimeStamp, final String outputFormat)
		  throws ParseException {
	  return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(
			  inputFormat).parse(inputTimeStamp));
  }
  
  
}