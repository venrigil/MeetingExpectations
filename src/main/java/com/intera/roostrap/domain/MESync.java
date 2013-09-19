package com.intera.roostrap.domain;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MESync {

	private static final Logger logger = Logger.getLogger(MESync.class.getCanonicalName());
	/**
	 * Return all the Meeting
	 * 
	 * @param kind
	 *            : of kind Meeting
	 * @return Meeting
	 */
	public static Iterable<Entity> getAllMeetings(String kind) {
		
		
		return MESyncUtil.listEntities(kind, "mparticipantsString", null);
	}

	public static Iterable<Entity> getAllMeetingsById(String mcreatedByValue) {
		
			return MESyncUtil.listEntities("Meeting", "mcreatedBy", mcreatedByValue);
		}
	
	public static Iterable<Entity> getAllMeetingsBysyncDateOlder(String msyncDateValue, String midSBy, String midSFor) {
		
			return MESyncUtil.listEntitiesBysyncDateOlder("Meeting", "msyncDate", msyncDateValue, midSBy, midSFor);
		}
	/**
	 * Get Meeting entity
	 * 
	 * @param name
	 *            : name of the Meeting
	 * @return: Meeting entity
	 */
	public static Entity getMeeting(String meetingID) {
		Key getMeetingkey = KeyFactory.createKey("Meeting", meetingID);
		return MESyncUtil.findEntity(getMeetingkey);
	}
	
	public static Entity getContact(String pemail) {
		
		
		
		// This is key based query - will not work as we have id and name 
		return MESyncUtil.listEntity("Directory", "pemail", pemail);
	}
}
