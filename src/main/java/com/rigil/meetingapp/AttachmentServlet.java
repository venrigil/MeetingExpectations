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
public class AttachmentServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(AttachmentServlet.class.getCanonicalName());

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	super.doGet(req,resp);
	logger.log(Level.INFO, "Obtaining Attachment listing");
	String searchBy = req.getParameter("attachment-searchby");
	String searchFor = req.getParameter("query");
	PrintWriter out = resp.getWriter();
	if (searchFor == null || searchFor.equals("")) {
	  Iterable<Entity> entities = Attachment.getAllAttachments();
	  out.println(Util.writeJSON(entities));
	} else if (searchBy == null || searchBy.equals("actionItemID")) {
	  Iterable<Entity> entities = Attachment.getAttachment(searchFor);
	  out.println(Util.writeJSON(entities));
	} else if (searchBy != null && searchBy.equals("meeting")) {
	  Iterable<Entity> entities = Attachment.getAttachmentsForMeeting("Attachment", searchFor);
	  out.println(Util.writeJSON(entities));
	}
  }

  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	logger.log(Level.INFO, "Creating Attachment");
	try {
	SimpleDateFormat aisDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");

	String attachmentID = req.getParameter("attachmentID");
	
	String acreatedOnstr = req.getParameter("acreatedOn");
	Date acreatedOn = aisDateFormat.parse(acreatedOnstr);
	
	
	Boolean adeleted = Boolean.valueOf(req.getParameter("adeleted"));
	String adesc = req.getParameter("adesc");
	String afileName = req.getParameter("afileName");
	
	String amodifiedOnstr = req.getParameter("amodifiedOn");
	Date amodifiedOn = aisDateFormat.parse(amodifiedOnstr);
	
	String atype = req.getParameter("atype");
	String anoteID = req.getParameter("anoteID");
	String aparentType = req.getParameter("aparentType");
	String aparentID = req.getParameter("aparentID");
	
	Attachment.createOrUpdateAttachment(attachmentID, acreatedOn, adeleted,
			adesc, afileName, amodifiedOn,
			atype, anoteID, aparentType, aparentID);
	
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
			}

  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException {
	super.doGet(req, resp);
	logger.log(Level.INFO, "Deleting Attachment");
	String attachmentKey = req.getParameter("attachmentID");
	PrintWriter out = resp.getWriter();
	Iterable<Entity> entities = Util.listEntities("Attachment", "attachmentID", attachmentKey);
	try {
	  for (Entity e : entities) {
		if (e != null)
		  out.print("Cannot delete Attachment as there are Attachments associated with this Attachment.");
		return;
	  }
	  Entity e = Attachment.getSingleAttachment(attachmentKey);
	  Util.deleteFromCache(e.getKey());
	  Util.deleteEntity(e.getKey());
	  out.print("Attachment deleted successfully.");
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