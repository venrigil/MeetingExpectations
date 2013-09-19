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
public class AgendaitemServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(AgendaitemServlet.class.getCanonicalName());

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  	super.doGet(req,resp);
    logger.log(Level.INFO, "Obtaining Agendaitem listing");
    String searchBy = req.getParameter("agendaitem-searchby");
    String searchFor = req.getParameter("query");
    PrintWriter out = resp.getWriter();
    if (searchFor == null || searchFor.equals("")) {
      Iterable<Entity> entities = Agendaitem.getAllAgendaitems();
      out.println(Util.writeJSON(entities));
    } else if (searchBy == null || searchBy.equals("agendaItemID")) {
      Iterable<Entity> entities = Agendaitem.getAgendaitem(searchFor);
      out.println(Util.writeJSON(entities));
    } else if (searchBy != null && searchBy.equals("meeting")) {
      Iterable<Entity> entities = Agendaitem.getAgendaitemsForMeeting("Agendaitem", searchFor);
      out.println(Util.writeJSON(entities));
    }
  }

  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    logger.log(Level.INFO, "Creating Agendaitem");
    try {
    SimpleDateFormat agisDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
    
    
    String agendaItemID = req.getParameter("agendaItemID");
    String agiAddlColumn1 = req.getParameter("agiAddlColumn1");
    String agiAddlColumn2 = req.getParameter("agiAddlColumn2");
    String agiAddlColumn3 = req.getParameter("agiAddlColumn3");
    String agiAddlColumn4 = req.getParameter("agiAddlColumn4");
    String agiAddlColumn5 = req.getParameter("agiAddlColumn5");
    String agiAndroidCreated = req.getParameter("agiAndroidCreated");
    String agiOtherCreated = req.getParameter("agiOtherCreated");
    String agiWebCreated = req.getParameter("agiWebCreated");
    String agiactionItems = req.getParameter("agiactionItems");
    





    
    
    String agiactualEndstr = req.getParameter("agiactualEnd");
    Date agiactualEnd = agisDateFormat.parse(agiactualEndstr);
    
    String agiactualStartstr = req.getParameter("agiactualStart");
    Date agiactualStart = agisDateFormat.parse(agiactualStartstr);	
  
    
    String agiagendaItems = req.getParameter("agiagendaItems");
    Boolean agicompleted = Boolean.valueOf(req.getParameter("agicompleted"));
    String agicreatedBy = req.getParameter("agicreatedBy");
    
    String agicreatedOnstr = req.getParameter("agicreatedOn");
    Date agicreatedOn = agisDateFormat.parse(agicreatedOnstr);	

    
    Boolean agideleted = Boolean.valueOf(req.getParameter("agideleted"));
    String agidesc = req.getParameter("agidesc");
    Integer agiduration = Integer.parseInt(req.getParameter("agiduration"));
    String agiiPadAppCreated = req.getParameter("agiiPadAppCreated");
    String agiid = req.getParameter("agiid");
    String agimeetingID = req.getParameter("agimeetingID");
    String agimeetings = req.getParameter("agimeetings");
    String agimodifiedOnstr = req.getParameter("agimodifiedOn");
    Date agimodifiedOn = agisDateFormat.parse(agimodifiedOnstr);
    
    String aginotes = req.getParameter("aginotes");
    Integer agiorder = Integer.parseInt(req.getParameter("agiorder"));
    String agiparticipants = req.getParameter("agiparticipants");
    String agipresenter = req.getParameter("agipresenter");
    String agisyncDatestr = req.getParameter("agisyncDate");
    Date agisyncDate = agisDateFormat.parse(agisyncDatestr);
    
    String agititle = req.getParameter("agititle");
    String agitype = req.getParameter("agitype");	
	
    Agendaitem.createOrUpdateAgendaitem(agendaItemID,agiAddlColumn1,agiAddlColumn2,agiAddlColumn3,
    	agiAddlColumn4,agiAddlColumn5,agiAndroidCreated,agiOtherCreated,agiWebCreated,agiactionItems,
    	agiactualEnd,agiactualStart,agiagendaItems,agicompleted,agicreatedBy,agicreatedOn,agideleted,
    	agidesc,agiduration,agiiPadAppCreated,agiid,agimeetingID,agimeetings,agimodifiedOn,aginotes,
    	agiorder,agiparticipants,agipresenter,agisyncDate,agititle,agitype);
    
    } catch (ParseException e) {
        e.printStackTrace();
    }
    
    		}

  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  	super.doGet(req, resp);
  	logger.log(Level.INFO, "Deleting Agendaitem");
    String agendaitemKey = req.getParameter("AgendaitemID");
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = Util.listEntities("Actionitem", "ActionitemID", agendaitemKey);
    try {
      for (Entity e : entities) {
        if (e != null)
          out.print("Cannot delete Agendaitem as there are Actionitems associated with this Agendaitem.");
        return;
      }
      Entity e = Agendaitem.getSingleAgendaitem(agendaitemKey);
      Util.deleteFromCache(e.getKey());
      Util.deleteEntity(e.getKey());
      out.print("Agendaitem deleted successfully.");
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