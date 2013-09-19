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

import java.util.Iterator;
import java.util.ResourceBundle;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.lang.Integer;

import javax.servlet.ServletException;

/**
 * This servlet pre populates some data into customers and products
 * 
 * @author
 */
@SuppressWarnings("serial")
public class PrePopulateDataServlet extends BaseServlet {

  @Override
  public void init() throws ServletException {
    populateMeeting();
    populateDirectory();
    populateAgendaitem();
    populateNote();
    populateActionitem();
    populateAttachment();
    
    
  }

  private void populateMeeting() {
    ResourceBundle rb = ResourceBundle.getBundle("Meeting");
    Iterator<String> keys = rb.keySet().iterator();
    String prop;
    while(keys.hasNext()){
      prop = rb.getString(keys.next());
      String values[] = prop.split(",");
      try {
	  	    SimpleDateFormat ppdsmsDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
		    
	        
	  	  
	  	  
	      Meeting.createOrUpdateMeeting(values[0], values[1],
	  			values[2],  values[3],  values[4],  ppdsmsDateFormat.parse(values[5]), 
	  Integer.parseInt(values[6]), ppdsmsDateFormat.parse(values[7]),  Integer.parseInt(values[8]), ppdsmsDateFormat.parse(values[9]),
	  Integer.parseInt(values[10]), ppdsmsDateFormat.parse(values[11]), ppdsmsDateFormat.parse(values[12]),  
	  Integer.parseInt(values[13]),  values[14],  Boolean.valueOf(values[15]),  
	  ppdsmsDateFormat.parse(values[16]), values[17],  values[18],  values[19],  values[20], 
	  			  values[21], values[22],  values[23],  values[24],  values[25], 
	  			  values[26], values[27],  values[28],  values[29],  values[30], 
	  			  values[31], values[32],  values[33]);
	      } catch (ParseException e) {
	          e.printStackTrace();
	      }
    }
  }

  private void populateDirectory() {
    ResourceBundle rb = ResourceBundle.getBundle("Directory");
    Iterator<String> keys = rb.keySet().iterator();
    String prop;
    while(keys.hasNext()){
      prop = rb.getString(keys.next());
      String values[] = prop.split(",");
      try {
	  	    SimpleDateFormat ppdsdDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");

      Directory.createOrUpdateDirectory(values[0], values[1],
  			values[2],  values[3], ppdsdDateFormat.parse(values[4]), Boolean.valueOf(values[5]),
  			ppdsdDateFormat.parse(values[6]), ppdsdDateFormat.parse(values[7]), values[8], values[9],  
  			   values[10], values[11],  values[12],  values[13], values[14], values[15],  values[16], 
  			   values[17],  values[18], values[19], values[20], values[21], values[22]);

      } catch (ParseException e) {
          e.printStackTrace();
      }
    }
  }
  
  private void populateAgendaitem() {
	    ResourceBundle rb = ResourceBundle.getBundle("Agendaitem");
	    Iterator<String> keys = rb.keySet().iterator();
	    String prop;
        
	    while(keys.hasNext()){
	      prop = rb.getString(keys.next());
	      String values[] = prop.split(",");
	      
	      try {
	  	    SimpleDateFormat ppdsagisDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
		    
	        
	  	  
	    	  
	      Agendaitem.createOrUpdateAgendaitem(values[0], values[1],  values[2],  values[3],
	    		  values[4], values[5],  values[6],  values[7],  values[8], values[9],
	    		  ppdsagisDateFormat.parse(values[10]),  ppdsagisDateFormat.parse(values[11]),  values[12], 
	    		  Boolean.valueOf(values[13]),
	    		  values[14],
	    		  ppdsagisDateFormat.parse(values[15]), Boolean.valueOf(values[16]),  values[17],  
	    		 
	    		  Integer.parseInt(values[18]),
	    		  values[19], 
	    		  values[20],
	    		  values[21], values[22], ppdsagisDateFormat.parse(values[23]),  values[24],  Integer.parseInt(values[25]), values[26],
	    		  values[27], ppdsagisDateFormat.parse(values[28]),  values[29],  values[30] );
	      } catch (ParseException e) {
	          e.printStackTrace();
	      }
	    }
	  }
  
  private void populateNote() {
	    ResourceBundle rb = ResourceBundle.getBundle("Note");
	    Iterator<String> keys = rb.keySet().iterator();
	    String prop;
      
	    while(keys.hasNext()){
	      prop = rb.getString(keys.next());
	      String values[] = prop.split(",");
	      
	      try {
	  	    SimpleDateFormat ppdsnsDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
    	  
	      Notes.createOrUpdateNote(values[0], values[1], values[2], 
	    		  ppdsnsDateFormat.parse(values[3]),  ppdsnsDateFormat.parse(values[4]), ppdsnsDateFormat.parse(values[5]),  
	    		  values[6], values[7], values[8], values[9],  
	    		     Boolean.valueOf(values[10]), values[11],  values[12],  values[13],
	    		     values[14], values[15],  values[16],  values[17], 
	    		     values[18], values[19], values[20],
	    		     values[21], values[22], values[23], values[24]);
	      } catch (ParseException e) {
	          e.printStackTrace();
	      }
	    }
	  }
  
  private void populateActionitem() {
	    ResourceBundle rb = ResourceBundle.getBundle("Actionitem");
	    Iterator<String> keys = rb.keySet().iterator();
	    String prop;
    
	    while(keys.hasNext()){
	      prop = rb.getString(keys.next());
	      String values[] = prop.split(",");
	      
	      try {
	  	    SimpleDateFormat ppaciDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
  	  
	  	  Actionitem.createOrUpdateActionitem(values[0], values[1], values[2], Boolean.valueOf(values[3]),
	  			values[4], ppaciDateFormat.parse(values[5]),  ppaciDateFormat.parse(values[6]), ppaciDateFormat.parse(values[7]),  
	  			values[8], Integer.parseInt(values[9]), ppaciDateFormat.parse(values[10]), values[11],  
	  			   values[12], values[13], values[14],
	  			   values[15], values[16], values[17], 
	  			   values[18], values[19], values[20],
	  			   values[21], values[22], values[23],
	  			   values[24], values[25], values[26], values[27]);
	      } catch (ParseException e) {
	          e.printStackTrace();
	      }
	    }
	  }
  
  
  private void populateAttachment() {
	    ResourceBundle rb = ResourceBundle.getBundle("Attachment");
	    Iterator<String> keys = rb.keySet().iterator();
	    String prop;
    
	    while(keys.hasNext()){
	      prop = rb.getString(keys.next());
	      String values[] = prop.split(",");
	      
	      try {
	  	    SimpleDateFormat ppdaDateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm:ss z");
  	  
	  	  Attachment.createOrUpdateAttachment(values[0], ppdaDateFormat.parse(values[1]), Boolean.valueOf(values[2]),
	  			values[3], values[4], ppdaDateFormat.parse(values[5]),
	  			values[6], values[7], values[8], values[9]);
	      } catch (ParseException e) {
	          e.printStackTrace();
	      }
	    }
	  }
  
  

}
