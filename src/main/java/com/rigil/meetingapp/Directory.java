package com.rigil.meetingapp;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class defines the methods for basic operations of create, update & retrieve
 * for Directory entity
 * 
 * @author 
 *
 */
public class Directory {

	/**
	 * Checks if the entity is existing and if it is not, it creates the entity
	 * else it updates the entity
	 * 
	 * @param nickName
	 *          : username for the directory
	 * @param firstName
	 *          : first name of the directory
	 * @param lastName
	 *          : last name of the directory
	 * @param address
	 *          : address of directory
	 * @param phone
	 *          : contact phone number
	 * @param email
	 *          : email id of directory
	 *          contactID, createdOn, deleted, email, firstName, lastName, modifiedOn
	 */
	public static void createOrUpdateDirectory(String pfirstName, String plastName, String pemail, 
			String contactID, Date pcreatedOn, Boolean pdeleted, Date pmodifiedOn, Date psyncDate, String pmeetings, 
			String pagendaItems, String pparticipants, String pnotes, String pactionItems, String pWebCreated, 
			String piPadAppCreated, String pAndroidCreated, String pOtherCreated, String pAddlColumn1, String pAddlColumn2, 
			String pAddlColumn3, String pAddlColumn4, String pAddlColumn5, String pcreatedBy) {
    Entity directory = getSingleDirectory(contactID);
    if (directory == null) {
      directory = new Entity("Directory", contactID);
      directory.setProperty("contactID", contactID);
      directory.setProperty("pfirstName", pfirstName);
      directory.setProperty("plastName", plastName);
      directory.setProperty("pemail", pemail);
      directory.setProperty("pcreatedOn", pcreatedOn);
      directory.setProperty("pdeleted", pdeleted);
      directory.setProperty("pmodifiedOn", pmodifiedOn);
      directory.setProperty("psyncDate", psyncDate);
      directory.setProperty("pmeetings", pmeetings);
      directory.setProperty("pagendaItems", pagendaItems);
      directory.setProperty("pparticipants", pparticipants);
      directory.setProperty("pnotes", pnotes);
      directory.setProperty("pactionItems", pactionItems);
      directory.setProperty("pWebCreated", pWebCreated);
      directory.setProperty("piPadAppCreated", piPadAppCreated);
      directory.setProperty("pAndroidCreated", pAndroidCreated);
      directory.setProperty("pOtherCreated", pOtherCreated);
      directory.setProperty("pAddlColumn1", pAddlColumn1);
      directory.setProperty("pAddlColumn2", pAddlColumn2);
      directory.setProperty("pAddlColumn3", pAddlColumn3);
      directory.setProperty("pAddlColumn4", pAddlColumn4);
      directory.setProperty("pAddlColumn5", pAddlColumn5);
      directory.setProperty("pcreatedBy", pcreatedBy);
    } else {
    	 if (contactID != null && !"".equals(contactID)) {
 	        directory.setProperty("contactID", contactID);
 	      }
    	   if (pfirstName != null && !"".equals(pfirstName)) {
    	        directory.setProperty("pfirstName", pfirstName);
    	      }
    	      if (plastName != null && !"".equals(plastName)) {
    	        directory.setProperty("plastName", plastName);
    	      }
    	      if (pemail != null && !"".equals(pemail)) {
    	          directory.setProperty("pemail", pemail);
    	      }
    	      if (pcreatedOn != null && !"".equals(pcreatedOn)) {
    	          directory.setProperty("pcreatedOn", pcreatedOn);
    	      }
    	      if (pdeleted != null && !"".equals(pdeleted)) {
    	          directory.setProperty("pdeleted", pdeleted);
    	      }
    	      if (pmodifiedOn != null && !"".equals(pmodifiedOn)) {
    	        directory.setProperty("pmodifiedOn", pmodifiedOn);
    	      }
    	      if (psyncDate != null && !"".equals(psyncDate)) {
    	        directory.setProperty("psyncDate", psyncDate);
    	      }
    	      if (pmeetings != null && !"".equals(pmeetings)) {
    	        directory.setProperty("pmeetings", pmeetings);
    	      }
    	      if (pagendaItems != null && !"".equals(pagendaItems)) {
    	          directory.setProperty("pagendaItems", pagendaItems);
    	      }
    	      if (pparticipants != null && !"".equals(pparticipants)) {
    	          directory.setProperty("pparticipants", pparticipants);
    	      }
    	      if (pnotes != null && !"".equals(pnotes)) {
    	          directory.setProperty("pnotes", pnotes);
    	      }
    	      if (pactionItems != null && !"".equals(pactionItems)) {
    	        directory.setProperty("pactionItems", pactionItems);
    	      }
    	      if (pWebCreated != null && !"".equals(pWebCreated)) {
    	        directory.setProperty("pWebCreated", pWebCreated);
    	      }
    	      if (piPadAppCreated != null && !"".equals(piPadAppCreated)) {
    	        directory.setProperty("piPadAppCreated", piPadAppCreated);
    	      }
    	      if (pAndroidCreated != null && !"".equals(pAndroidCreated)) {
    	          directory.setProperty("pAndroidCreated", pAndroidCreated);
    	      }
    	      if (pOtherCreated != null && !"".equals(pOtherCreated)) {
    	          directory.setProperty("pOtherCreated", pOtherCreated);
    	      }
    	      if (pAddlColumn1 != null && !"".equals(pAddlColumn1)) {
    	          directory.setProperty("pAddlColumn1", pAddlColumn1);
    	      }
    	      if (pAddlColumn2 != null && !"".equals(pAddlColumn2)) {
    	        directory.setProperty("pAddlColumn2", pAddlColumn2);
    	      }
    	      if (pAddlColumn3 != null && !"".equals(pAddlColumn3)) {
    	        directory.setProperty("pAddlColumn3", pAddlColumn3);
    	      }
    	      if (pAddlColumn4 != null && !"".equals(pAddlColumn4)) {
    	        directory.setProperty("pAddlColumn4", pAddlColumn4);
    	      }
    	      if (pAddlColumn5 != null && !"".equals(pAddlColumn5)) {
    	          directory.setProperty("pAddlColumn5", pAddlColumn5);
    	      }
    	      if (pcreatedBy != null && !"".equals(pcreatedBy)) {
    	          directory.setProperty("pcreatedBy", pcreatedBy);
    	      }
    	    
    }
    Util.persistEntity(directory);
  }

	/**
	 * List all the directorys available
	 * 
	 * @return an iterable list with all the directorys
	 */
  public static Iterable<Entity> getAllDirectorys() {
    Iterable<Entity> entities = Util.listEntities("Directory", null, null);
    return entities;
  }

	/**
	 * Searches for a directory and returns the entity as an iterable The search is
	 * performed by creating a query and searching for the attribute
	 * 
	 * @param directoryName
	 *          : username of the directory
	 * @return iterable with the directorys searched for
	 */
  public static Iterable<Entity> getDirectory(String contactID) {
    Iterable<Entity> entities = Util.listEntities("Directory", "contactID",	contactID);
    return entities;
  }

	/**
	 * Searches for a directory and returns the entity as an iterable The search is
	 * key based instead of query
	 * 
	 * @param directoryName
	 *          : username of the directory
	 * @return the entity with the username as key
	 */
  public static Entity getSingleDirectory	(String contactID) {
    Key key = KeyFactory.createKey("Directory", contactID);
    return Util.findEntity(key);
  }
}
