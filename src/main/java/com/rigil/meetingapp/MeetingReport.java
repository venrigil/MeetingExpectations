package com.rigil.meetingapp;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for the product entity
 * 
 * @author
 * 
 */
public class MeetingReport {



	/**
	 * Return all the Meeting
	 * 
	 * @param kind
	 *            : of kind Meeting
	 * @return Meeting
	 */
	public static Iterable<Entity> getAllMeetings(String kind) {
		return MRUtil.listEntities(kind, null, null);
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
		return MRUtil.findEntity(key);
	}
}
