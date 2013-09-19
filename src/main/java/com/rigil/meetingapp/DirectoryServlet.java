package com.rigil.meetingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * This servlet responds to the request corresponding to Directory. The class
 * creates and manages the Directory Entity
 * 
 * @author
 */
@SuppressWarnings("serial")
public class DirectoryServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(DirectoryServlet.class.getCanonicalName());

	/**
	 * Get the requested directory entities in JSON format
	 */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    super.doGet(req, resp);
    logger.log(Level.INFO, "Obtaining Directory listing");
    String searchFor = req.getParameter("q");
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    if (searchFor == null || searchFor.equals("")) {
      entities = Directory.getAllDirectorys();
      out.println(Util.writeJSON(entities));
    } else {
      entities = Directory.getDirectory(searchFor);
      out.println(Util.writeJSON(entities));
    }
    return;
  }

	/**
	 * Insert the new directory
	 */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    logger.log(Level.INFO, "Creating Directory");
    PrintWriter out = resp.getWriter();
    
    try {
        SimpleDateFormat msDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
    String pfirstName = req.getParameter("pfirstName");
    String plastName = req.getParameter("plastName");
    String pemail = req.getParameter("pemail");
    String contactID = req.getParameter("contactID");
    
    String pcreatedOnstr = req.getParameter("pcreatedOn");
    Date pcreatedOn = msDateFormat.parse(pcreatedOnstr);
    
    Boolean pdeleted = Boolean.valueOf(req.getParameter("pdeleted"));
    
    String pmodifiedOnstr = req.getParameter("pmodifiedOn");
    Date pmodifiedOn = msDateFormat.parse(pmodifiedOnstr);
    
    String psyncDatestr = req.getParameter("psyncDate");
    Date psyncDate = msDateFormat.parse(psyncDatestr);
    
    String pmeetings = req.getParameter("pmeetings");
    String pagendaItems = req.getParameter("pagendaItems");
    String pparticipants = req.getParameter("pparticipants");
    String pnotes = req.getParameter("pnotes");
    String pactionItems = req.getParameter("pactionItems");
    String pWebCreated = req.getParameter("pWebCreated");
    String piPadAppCreated = req.getParameter("piPadAppCreated");
    String pAndroidCreated = req.getParameter("pAndroidCreated");
    String pOtherCreated = req.getParameter("pOtherCreated");
    String pAddlColumn1 = req.getParameter("pAddlColumn1");
    String pAddlColumn2 = req.getParameter("pAddlColumn2");
    String pAddlColumn3 = req.getParameter("pAddlColumn3");
    String pAddlColumn4 = req.getParameter("pAddlColumn4");
    String pAddlColumn5 = req.getParameter("pAddlColumn5");
    String pcreatedBy = req.getParameter("pcreatedBy");
    
    Directory.createOrUpdateDirectory(pfirstName, plastName, pemail, contactID, 
    		pcreatedOn, pdeleted, pmodifiedOn, psyncDate, pmeetings, pagendaItems, pparticipants, pnotes, 
    		pactionItems, pWebCreated, piPadAppCreated, pAndroidCreated, pOtherCreated, pAddlColumn1, pAddlColumn2, 
    		pAddlColumn3, pAddlColumn4, pAddlColumn5, pcreatedBy);
    } catch (ParseException e) {
        e.printStackTrace();
    } catch (Exception e) {
      String msg = Util.getErrorResponse(e);
      out.print(msg);
    }
  }
  
	/**
	 * Delete the directory
	 */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    String customerName = req.getParameter("contactID");
    logger.log(Level.INFO, "Deleting Directory {0}", customerName);
    Key key = KeyFactory.createKey("Directory", customerName);
    try {
      Iterable<Entity> entities = Util.listChildKeys("Actionitem", key);
      final List<Key> actionitemkeys = new ArrayList<Key>();
 /*     final List<Key> linekeys = new ArrayList<Key>();
      for (Entity e : entities) {
        orderkeys.add(e.getKey());
        Iterable<Entity> lines = Util.listEntities("LineItem", "orderID",String.valueOf(e.getKey().getId()));
        for(Entity en : lines){
          linekeys.add(en.getKey());
        }
      }
      Util.deleteEntity(linekeys); */
      Util.deleteEntity(actionitemkeys);
      Util.deleteFromCache(key);
      Util.deleteEntity(key);
    } catch (Exception e) {
      String msg = Util.getErrorResponse(e);
      resp.getWriter().print(msg);
    }
  }

	/**
	 * Redirect the call to doDelete or doPut method
	 */
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
      doPut(req, resp);
  }
}
