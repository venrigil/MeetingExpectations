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
import com.intera.roostrap.domain.MESyncUtil;
import com.rigil.meetingapp.WMRUtil;

import java.util.Map;
/**
 * This servlet responds to the request corresponding to meetings. The servlet
 * manages the Meeting Entity
 * 
 * @author
 */
@SuppressWarnings("serial")
public class MESyncServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(MESyncServlet.class.getCanonicalName());

  /**
   * Get the entities in JSON format.
  */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			      throws ServletException, IOException {
    super.doGet(req, resp);
    //logger.log(Level.INFO, "Obtaining Meeting listing - By id and sdate - start");
    
    
    
    
	
	
    String searchFor = req.getParameter("query");
	String searchForid = req.getParameter("id");
	String searchForDate = req.getParameter("sdate");
	
	
	//logger.log(Level.INFO,"searchForDate"+searchForDate  +"  ");
	String searchContactID = "";
	String searchsyncDatemID = "";
    
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    Iterable<Entity> entitiesmSyncDate = null;
	 Iterable<Entity> DSMentities = null;
    
 	
	StringBuilder MESyncSsb = new StringBuilder();
	StringBuilder MListsb = new StringBuilder();
	StringBuilder MListsbmSyncDate = new StringBuilder();
	StringBuilder MListsbmSyncDateFinal = new StringBuilder();
	
	
	
	StringBuilder MListsyncDatesb = new StringBuilder();
	StringBuilder MEResultListsb = new StringBuilder();
	
	/*
 	if (searchForid != null) {
 		logger.log(Level.INFO,"In searchForid Loop");
    	entities = MESync.getAllMeetingsById(searchForid);
    	if (entities != null) {
    		out.println(MESyncUtil.writeJSON(entities));
	        }
    	else {
    		out.println("[]");    		
    	}
    	logger.log(Level.INFO, "Is ERROR? " + out.checkError() + "  ");
 	}
	*/
	
    if (searchForid != null && searchForDate == null) {
    	
    	
    	
		entities = MESync.getAllMeetingsById(searchForid);
		if (entities != null) {
		for (Entity MListresult : entities) {
			
			Map<String, Object> MListproperties = MListresult.getProperties();
			for (String MListkey : MListproperties.keySet()) {
				
			if (MListkey.equals("meetingID")) {
				
				MListsb.append(MListproperties.get(MListkey) + ";");	
				
			}
			
			}	
		}
		}
		
		Entity Contacte = MESync.getContact(searchForid);
		
    	if (Contacte != null) {
    		
		searchContactID = Contacte.getProperty("contactID").toString();
		
		DSMentities = MESync.getAllMeetings("Meeting");
		if (DSMentities != null) {
			
			for (Entity CMListresult : DSMentities) {
				
				Map<String, Object> CMListproperties = CMListresult.getProperties();
				for (String CMListkey : CMListproperties.keySet()) {
					
					if (CMListkey.equals("mparticipantsString")) {
					String mspstr = String.valueOf(CMListproperties.get(CMListkey));
					
					String[] msptemp;
					String mspdelimiter = ";";
					msptemp = mspstr.split(mspdelimiter);
						for (int mspsi = 0; mspsi < msptemp.length; mspsi++) {
							if (msptemp[mspsi] != null) {
								
								
								if (searchContactID.equalsIgnoreCase(msptemp[mspsi].toString())) {
									
								
									String MListMID = String.valueOf(CMListproperties.get("meetingID"));
									if (MListMID != null) {
										
										
										if (MListsb.indexOf(MListMID) == -1) {
											
											MListsb.append(String.valueOf(CMListproperties.get("meetingID")) + ";");
											
										}
										
									}
									
								}
							}
						}
					}
				}
			}
		}
	    }
    	String MListsbstr = "";
    	if (MListsb != null) {
    		MListsbstr = MListsb.toString();
    	}
		String[] MListsbtemp;
		String MListsbdelimiter = ";";
		MListsbtemp = MListsbstr.split(MListsbdelimiter);
		MEResultListsb.append("[");
		for (int MListsbsi = 0; MListsbsi < MListsbtemp.length; MListsbsi++) {
			if (MListsbtemp[MListsbsi] != null) {
		    	Entity MEResulte = MESync.getMeeting(MListsbtemp[MListsbsi]);
		    	if (MEResulte != null) {



MEResultListsb.append(MESyncUtil.writeReportEntityJSON(MEResulte, "Agendaitem", "Note", "Actionitem", "Attachment", "agimeetingID", "nparentID","aciparentID", "aparentID"));
				if (MListsbsi < MListsbtemp.length-1) {
					MEResultListsb.append(",");	
				}
		    	}
			}
		}
		MEResultListsb.append("]");
		
		out.println(MEResultListsb);	
		
		
		
		
		
		} else if (searchForid != null && searchForDate != null) {
	    	
			entities = MESync.getAllMeetingsById(searchForid);
			
			if (entities != null) {
			for (Entity MListresult : entities) {
				
				Map<String, Object> MListproperties = MListresult.getProperties();
				for (String MListkey : MListproperties.keySet()) {
					

				
				
					
					
					
					
					
					
					
				if (MListkey.equals("meetingID")) {
					
					
					
						MListsb.append(MListproperties.get(MListkey) + ";");
						
						
					
				}
				}	
			}
			}
			//logger.log(Level.INFO,"MListsb"+MListsb+"  ");
			//logger.log(Level.INFO,"MListsbmSyncDate"+MListsbmSyncDate+"  ");
			
			
			Entity Contacte = MESync.getContact(searchForid);
			
	    	if (Contacte != null) {
	    		
			searchContactID = Contacte.getProperty("contactID").toString();
			
			
			
			
			entitiesmSyncDate = MESync.getAllMeetingsBysyncDateOlder(searchForDate, "meetingID", searchForid);
			if (entitiesmSyncDate != null) {
				
				
				for (Entity CMListresult : entitiesmSyncDate) {
					
					
					Map<String, Object> CMListproperties = CMListresult.getProperties();
					for (String CMListkey : CMListproperties.keySet()) {
						
						if (CMListkey.equals("mparticipantsString")) {
						String mspstr = String.valueOf(CMListproperties.get(CMListkey));
						
						String[] msptemp;
						String mspdelimiter = ";";
						msptemp = mspstr.split(mspdelimiter);
							for (int mspsi = 0; mspsi < msptemp.length; mspsi++) {
								if (msptemp[mspsi] != null) {
									
									
									if (searchContactID.equalsIgnoreCase(msptemp[mspsi].toString())) {
										
									
										String MListMID = String.valueOf(CMListproperties.get("meetingID"));
										if (MListMID != null) {
											
											
											if (MListsb.indexOf(MListMID) == -1) {
												
												MListsb.append(String.valueOf(CMListproperties.get("meetingID")) + ";");
												
											}
											
										}
										
									}
								}
							}
						}
					}
				}
			}
		    }
	    	String MListsbstr = "";
	    	
	    	if (MListsb != null) {
	    		MListsbstr = MListsb.toString();
	    	}
			//logger.log(Level.INFO,"MListsbstr"+MListsbstr+"  ");
			
			
			
			if (entitiesmSyncDate != null) {
			for (Entity MListresultmSyncDate : entitiesmSyncDate) {
				
				Map<String, Object> MListpropertiesmSyncDate = MListresultmSyncDate.getProperties();
				for (String MListkeymSyncDate : MListpropertiesmSyncDate.keySet()) {
					

				
				
					
					
					
					
					
					
					
				if (MListkeymSyncDate.equals("meetingID")) {
					
					
					
					MListsbmSyncDate.append(MListpropertiesmSyncDate.get(MListkeymSyncDate) + ";");
						
						
					
				}
				}	
			}
			}
	    	String MListsbstrmSyncDate = "";
	    	
	    	if (MListsbmSyncDate != null) {
	    		MListsbstrmSyncDate = MListsbmSyncDate.toString();
	    	}
			//logger.log(Level.INFO,"MListsbstrmSyncDate"+MListsbstrmSyncDate+"  ");
			
			
			String[] msptemp1;
			String mspdelimiter1 = ";";
			msptemp1 = MListsbstr.split(mspdelimiter1);
				for (int mspsi1 = 0; mspsi1 < msptemp1.length; mspsi1++) {
					if (msptemp1[mspsi1] != null) {
						if(MListsbmSyncDate.indexOf(msptemp1[mspsi1]) >= 0) {
							MListsbmSyncDateFinal.append(msptemp1[mspsi1] + ";");
						}
					}
				}
				
				String MListsbstrmSyncDateFinal = "";
		    	
		    	if (MListsbmSyncDateFinal != null) {
		    		MListsbstrmSyncDateFinal = MListsbmSyncDateFinal.toString();
		    	}
				//logger.log(Level.INFO,"MListsbstrmSyncDateFinal"+MListsbstrmSyncDateFinal+"  ");
				
			
	    	/*
			
			String[] msptempDate;
			String mspdelimiterDate = ";";
			msptempDate = MListsbstr.split(mspdelimiterDate);
				for (int mspsiDate = 0; mspsiDate < msptempDate.length; mspsiDate++) {
					if (msptempDate[mspsiDate] != null) {
				    	Entity MEResulteDate = MESync.getMeeting(msptempDate[mspsiDate]);
				    	if (MEResulteDate != null) {
				    			searchsyncDatemID = MEResulteDate.getProperty("msyncDate").toString();
				    			logger.log(Level.INFO,"searchsyncDatemID"+searchsyncDatemID+"  ");
				    	}
					}
				}
							
										
			*/						
	    	
	    	String[] MListsbtemp;
			String MListsbdelimiter = ";";
			MListsbtemp = MListsbstrmSyncDateFinal.split(MListsbdelimiter);
			MEResultListsb.append("[");
			
			
			if (entitiesmSyncDate != null)
			{
				//logger.log(Level.INFO,"MListsbtemp.length"+MListsbtemp.length+"  ");
			for (int MListsbsi = 0; MListsbsi < MListsbtemp.length; MListsbsi++) {
				if (!MListsbtemp[MListsbsi].isEmpty()) {
					//logger.log(Level.INFO,"MListsbtemp[MListsbsi]"+MListsbtemp[MListsbsi]+"  ");
			    	Entity MEResulte = MESync.getMeeting(MListsbtemp[MListsbsi]);
			    	if (MEResulte != null) {
	
	

	MEResultListsb.append(MESyncUtil.writeReportEntityJSON(MEResulte, "Agendaitem", "Note", "Actionitem", "Attachment", "agimeetingID", "nparentID","aciparentID", "aparentID"));
					if (MListsbsi < MListsbtemp.length-1) {
						MEResultListsb.append(",");	
					}
			    	}
				}
			}
			}
			MEResultListsb.append("]");
			
			out.println(MEResultListsb);	
		
			
			
			
		}
	        
	        
	        
			
			

    	else {
    		out.println("[]");    		
		}
	        
    	
    	
	      
	        
	        //out.println(MESyncUtil.writeEntityJSON(e, "Agendaitem", "agimeetingID")); 



    	
	        
	        
	        
    
    	/*
      entities = Meeting.getAllMeetings("Meeting");
	      out.println(MESyncUtil.writeJSON(entities, "Agendaitem", "meetingID"));
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
   
  /*
	      Entity e = Meeting.getMeeting(searchFor);
    		
    		
	      if (e != null) {
		        Set<Entity> result = new HashSet<Entity>();
		        result.add(e);
		     
		        
		        
		        out.println(MESyncUtil.writeEntityJSON(e, "Agendaitem", "meetingID"));
		        
		      
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
}