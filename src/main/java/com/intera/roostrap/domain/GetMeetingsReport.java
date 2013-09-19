package com.intera.roostrap.domain;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rigil.meetingapp.WMRUtil;

	/**
	 * This class defines the methods for basic operations of create, update &
	 * retrieve for the product entity
	 * 
	 * @author
	 * 
	 */
	public class GetMeetingsReport {



		/**
		 * Return all the Meeting
		 * 
		 * @param kind
		 *            : of kind Meeting
		 * @return Meeting
		 */
		public static Iterable<Entity> getAllMeetings(String kind) {
			return WMRUtil.listEntities(kind, null, null);
		}

		public static Iterable<Entity> getAllMeetingsById(String mcreatedByValue) {
			return WMRUtil.listEntities("Meeting", "mcreatedBy", mcreatedByValue);
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
			return WMRUtil.findEntity(key);
		}
	}
