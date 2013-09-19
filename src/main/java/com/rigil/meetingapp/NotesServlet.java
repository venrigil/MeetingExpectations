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
public class NotesServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(NotesServlet.class.getCanonicalName());

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  	super.doGet(req,resp);
    logger.log(Level.INFO, "Obtaining Notes listing");
    String searchBy = req.getParameter("note-searchby");
    String searchFor = req.getParameter("query");
    PrintWriter out = resp.getWriter();
    if (searchFor == null || searchFor.equals("")) {
      Iterable<Entity> entities = Notes.getAllNotes();
      out.println(Util.writeJSON(entities));
    } else if (searchBy == null || searchBy.equals("noteID")) {
      Iterable<Entity> entities = Notes.getNote(searchFor);
      out.println(Util.writeJSON(entities));
    } else if (searchBy != null && searchBy.equals("meeting")) {
      Iterable<Entity> entities = Notes.getNotesForMeeting("Note", searchFor);
      out.println(Util.writeJSON(entities));
    }
  }

  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    logger.log(Level.INFO, "Creating Note");
    try {
    SimpleDateFormat nsDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
   
    String ntext = req.getParameter("ntext");
    String ncreatedBy = req.getParameter("ncreatedBy");
    String noteID = req.getParameter("noteID");
    
    String ncreatedOnstr = req.getParameter("ncreatedOn");
    Date ncreatedOn = nsDateFormat.parse(ncreatedOnstr);
        
    String nmodifiedOnstr = req.getParameter("nmodifiedOn");
    Date nmodifiedOn = nsDateFormat.parse(nmodifiedOnstr);
    
    String nsyncDatestr = req.getParameter("nsyncDate");
    Date nsyncDate = nsDateFormat.parse(nsyncDatestr);
    
    String ntitle = req.getParameter("ntitle");
    String nparentType = req.getParameter("nparentType");
    String nparentID = req.getParameter("nparentID");
    String nmaaID = req.getParameter("nmaaID");
    Boolean ndeleted = Boolean.valueOf(req.getParameter("ndeleted"));
    String nmeetings = req.getParameter("nmeetings");
    String nagendaItems = req.getParameter("nagendaItems");
    String nparticipants = req.getParameter("nparticipants");
    String nnotes = req.getParameter("nnotes");
    String nactionItems = req.getParameter("nactionItems");
    String nWebCreated = req.getParameter("nWebCreated");
    String niPadAppCreated = req.getParameter("niPadAppCreated");
    String nAndroidCreated = req.getParameter("nAndroidCreated");
    String nOtherCreated = req.getParameter("nOtherCreated");
    String nAddlColumn1 = req.getParameter("nAddlColumn1");
    String nAddlColumn2 = req.getParameter("nAddlColumn2");
    String nAddlColumn3 = req.getParameter("nAddlColumn3");
    String nAddlColumn4 = req.getParameter("nAddlColumn4");
    String nAddlColumn5 = req.getParameter("nAddlColumn5");
	
    Notes.createOrUpdateNote(ntext, ncreatedBy, noteID, ncreatedOn, nmodifiedOn, nsyncDate, 
    		ntitle, nparentType, nparentID, nmaaID, ndeleted, nmeetings, 
    		nagendaItems, nparticipants, nnotes, nactionItems, nWebCreated, 
    		niPadAppCreated, nAndroidCreated, nOtherCreated, nAddlColumn1, 
    		nAddlColumn2, nAddlColumn3, nAddlColumn4, nAddlColumn5);
    
    } catch (ParseException e) {
        e.printStackTrace();
    }
    
    		}

  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  	super.doGet(req, resp);
  	logger.log(Level.INFO, "Deleting Note");
    String noteitemKey = req.getParameter("noteID");
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = Util.listEntities("Note", "noteID", noteitemKey);
    try {
      for (Entity e : entities) {
        if (e != null)
          out.print("Cannot delete Note as there are Actionitems associated with this Note.");
        return;
      }
      Entity e = Notes.getSingleNote(noteitemKey);
      Util.deleteFromCache(e.getKey());
      Util.deleteEntity(e.getKey());
      out.print("Note deleted successfully.");
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