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

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.ArrayList;
import java.util.List;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for the product entity
 * 
 * @author
 * 
 */
public class Meeting {
	/**
	 * Update or create the product
	 * 
	 * @param name
	 *            : name of the product
	 * @param description
	 *            : description
	 * @return updated product
	 */
	
	public static Entity createOrUpdateMeeting(String mtitle, String mdesc, String mlocation, String meetingID, String mid, Date mdate, 
			Integer mplannedDuration, Date mplannedStartDateTime, 
				Integer mplannedStart, Date mplannedEndDateTime, Integer mplannedEnd, Date msyncDate, Date mcreatedOn, Integer mactualDuration,
				String mcreatedBy,
				Boolean mdeleted, Date mmodifiedOn, String magendaitemsString, String mparticipantsString, String murl, String mmeetings, 
				String magendaItems, 
				String mparticipants, String mnotes, String mactionItems, String mWebCreated, String miPadAppCreated, String mAndroidCreated, 
				String mOtherCreated,
				String mAddlColumn1, String mAddlColumn2, String mAddlColumn3, String mAddlColumn4, String mAddlColumn5)
	 {
		Entity meeting = getMeeting(meetingID);
	    
    if (meeting == null) {
    	meeting = new Entity("Meeting", meetingID);
    	meeting.setProperty("meetingID", meetingID);
    	meeting.setProperty("mtitle", mtitle);
    	meeting.setProperty("mdesc", mdesc);
    	meeting.setProperty("mlocation", mlocation);
    	meeting.setProperty("mid", mid);
    	meeting.setProperty("mdate", mdate);
    	meeting.setProperty("mplannedDuration", mplannedDuration);
    	meeting.setProperty("mplannedStartDateTime", mplannedStartDateTime);
    	meeting.setProperty("mplannedStart", mplannedStart);
    	meeting.setProperty("mplannedEndDateTime", mplannedEndDateTime);
    	meeting.setProperty("mplannedEnd", mplannedEnd);
    	meeting.setProperty("msyncDate", msyncDate);
    	meeting.setProperty("mcreatedOn", mcreatedOn);
    	meeting.setProperty("mactualDuration", mactualDuration);
    	meeting.setProperty("mcreatedBy", mcreatedBy);
    	meeting.setProperty("mdeleted", mdeleted);
    	meeting.setProperty("mmodifiedOn", mmodifiedOn);
    	meeting.setProperty("magendaitemsString", magendaitemsString);
    	meeting.setProperty("mparticipantsString", mparticipantsString);
    	meeting.setProperty("murl", murl);
    	meeting.setProperty("mmeetings", mmeetings);
    	meeting.setProperty("magendaItems", magendaItems);
    	meeting.setProperty("mparticipants", mparticipants);
    	meeting.setProperty("mnotes", mnotes);
    	meeting.setProperty("mactionItems", mactionItems);
    	meeting.setProperty("mWebCreated", mWebCreated);
    	meeting.setProperty("miPadAppCreated", miPadAppCreated);
    	meeting.setProperty("mAndroidCreated", mAndroidCreated);
    	meeting.setProperty("mOtherCreated", mOtherCreated);
    	meeting.setProperty("mAddlColumn1", mAddlColumn1);
    	meeting.setProperty("mAddlColumn2", mAddlColumn2);
    	meeting.setProperty("mAddlColumn3", mAddlColumn3);
    	meeting.setProperty("mAddlColumn4", mAddlColumn4);
    	meeting.setProperty("mAddlColumn5", mAddlColumn5);
    }
	meeting.setProperty("meetingID", meetingID);
	meeting.setProperty("mtitle", mtitle);
	meeting.setProperty("mdesc", mdesc);
	meeting.setProperty("mlocation", mlocation);
	meeting.setProperty("mid", mid);
	meeting.setProperty("mdate", mdate);
	meeting.setProperty("mplannedDuration", mplannedDuration);
	meeting.setProperty("mplannedStartDateTime", mplannedStartDateTime);
	meeting.setProperty("mplannedStart", mplannedStart);
	meeting.setProperty("mplannedEndDateTime", mplannedEndDateTime);
	meeting.setProperty("mplannedEnd", mplannedEnd);
	meeting.setProperty("msyncDate", msyncDate);
	meeting.setProperty("mcreatedOn", mcreatedOn);
	meeting.setProperty("mactualDuration", mactualDuration);
	meeting.setProperty("mcreatedBy", mcreatedBy);
	meeting.setProperty("mdeleted", mdeleted);
	meeting.setProperty("mmodifiedOn", mmodifiedOn);
	meeting.setProperty("magendaitemsString", magendaitemsString);
	meeting.setProperty("mparticipantsString", mparticipantsString);
	meeting.setProperty("murl", murl);
	meeting.setProperty("mmeetings", mmeetings);
	meeting.setProperty("magendaItems", magendaItems);
	meeting.setProperty("mparticipants", mparticipants);
	meeting.setProperty("mnotes", mnotes);
	meeting.setProperty("mactionItems", mactionItems);
	meeting.setProperty("mWebCreated", mWebCreated);
	meeting.setProperty("miPadAppCreated", miPadAppCreated);
	meeting.setProperty("mAndroidCreated", mAndroidCreated);
	meeting.setProperty("mOtherCreated", mOtherCreated);
	meeting.setProperty("mAddlColumn1", mAddlColumn1);
	meeting.setProperty("mAddlColumn2", mAddlColumn2);
	meeting.setProperty("mAddlColumn3", mAddlColumn3);
	meeting.setProperty("mAddlColumn4", mAddlColumn4);
	meeting.setProperty("mAddlColumn5", mAddlColumn5);

	Util.persistEntity(meeting);
   return meeting;
 }

	/**
	 * Return all the Meeting
	 * 
	 * @param kind
	 *            : of kind Meeting
	 * @return Meeting
	 */
	public static Iterable<Entity> getAllMeetings(String kind) {
		return Util.listEntities(kind, null, null);
	}

	/**
	 * Get Meeting entity
	 * 
	 * @param name
	 *            : name of the Meeting
	 * @return: Meeting entity
	 */
	public static Entity getMeeting(String meetingID) {
		Key key = KeyFactory.createKey("Meeting", meetingID);
		return Util.findEntity(key);
	}
}
