package com.intera.roostrap.domain;

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
public class GetMeetingsReportServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(GetMeetingsReportServlet.class.getCanonicalName());

  /**
   * Get the entities in JSON format.
  */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    super.doGet(req, resp);
    logger.log(Level.INFO, "Obtaining Meeting listing");
    
    
    
    
	
	
    String searchFor = req.getParameter("query");
    String searchForid = req.getParameter("id");
    logger.log(Level.INFO, " "+searchFor+"");
    		logger.log(Level.INFO, " "+searchForid+"");
    
    PrintWriter out = resp.getWriter(); 
    PrintWriter setout2;
    Iterable<Entity> entities = null;
    String uri = req.getRequestURI();
 	logger.log(Level.INFO,""+uri  +"  ");
 	
 	if (searchForid != null) {
 		logger.log(Level.INFO,"In searchForid Loop");
    	entities = GetMeetingsReport.getAllMeetingsById(searchForid);
    	if (entities != null) {
    		out.println(WMRUtil.writeJSONWithTitle(entities,searchForid));
	        }
    	else {
    		out.println("[]");    		
    	}
    	logger.log(Level.INFO, "Is ERROR? " + out.checkError() + "  ");
 	}
    
    
  }

}