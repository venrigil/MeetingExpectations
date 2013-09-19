package com.rigil.meetingapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Agendaitem {

 public static Entity createOrUpdateAgendaitem(String agendaItemID,String agiAddlColumn1,String agiAddlColumn2,String agiAddlColumn3,String agiAddlColumn4,String agiAddlColumn5,String agiAndroidCreated,String agiOtherCreated,String agiWebCreated,String agiactionItems,Date agiactualEnd,Date agiactualStart,String agiagendaItems,Boolean agicompleted,String agicreatedBy,Date agicreatedOn,Boolean agideleted,String agidesc,Integer agiduration,String agiiPadAppCreated,String agiid,String agimeetingID,String agimeetings,Date agimodifiedOn,String aginotes,Integer agiorder,String agiparticipants,String agipresenter,Date agisyncDate,String agititle,String agitype) {

 	 
    Entity meeting = Meeting.getMeeting(agimeetingID);
    
    Entity agendaitem = getSingleAgendaitem(agendaItemID);
    if (agendaitem == null) {
    	
    	agendaitem = new Entity("Agendaitem", agendaItemID);
    	agendaitem.setProperty("agendaItemID", agendaItemID);
    	agendaitem.setProperty("agiAddlColumn1", agiAddlColumn1);
    	agendaitem.setProperty("agiAddlColumn2", agiAddlColumn2);
    	agendaitem.setProperty("agiAddlColumn3", agiAddlColumn3);
    	agendaitem.setProperty("agiAddlColumn4", agiAddlColumn4);
    	agendaitem.setProperty("agiAddlColumn5", agiAddlColumn5);
    	
    	agendaitem.setProperty("agiAndroidCreated", agiAndroidCreated);
    	agendaitem.setProperty("agiOtherCreated", agiOtherCreated);
    	agendaitem.setProperty("agiWebCreated", agiWebCreated);
    	agendaitem.setProperty("agiactionItems", agiactionItems);
    	agendaitem.setProperty("agiactualEnd", agiactualEnd);
    	
    	agendaitem.setProperty("agiactualStart", agiactualStart);
    	agendaitem.setProperty("agiagendaItems", agiagendaItems);
    	agendaitem.setProperty("agicompleted", agicompleted);
    	agendaitem.setProperty("agicreatedBy", agicreatedBy);
    	agendaitem.setProperty("agicreatedOn", agicreatedOn);
    	
    	agendaitem.setProperty("agideleted", agideleted);
    	agendaitem.setProperty("agidesc", agidesc);
    	agendaitem.setProperty("agiduration", agiduration);
    	agendaitem.setProperty("agiiPadAppCreated", agiiPadAppCreated);
    	agendaitem.setProperty("agiid", agiid);
    	
    	agendaitem.setProperty("agimeetingID", agimeetingID);
    	agendaitem.setProperty("agimeetings", agimeetings);
    	agendaitem.setProperty("agimodifiedOn", agimodifiedOn);
    	agendaitem.setProperty("aginotes", aginotes);
    	agendaitem.setProperty("agiorder", agiorder);

    	agendaitem.setProperty("agiparticipants", agiparticipants);
    	agendaitem.setProperty("agipresenter", agipresenter);
    	agendaitem.setProperty("agisyncDate", agisyncDate);
    	agendaitem.setProperty("agititle", agititle);
    	agendaitem.setProperty("agitype", agitype);
    	
    } else {
      if (agendaitem != null && !"".equals(agendaitem)) {
       //-
        	agendaitem.setProperty("agendaItemID", agendaItemID);
        	agendaitem.setProperty("agiAddlColumn1", agiAddlColumn1);
        	agendaitem.setProperty("agiAddlColumn2", agiAddlColumn2);
        	agendaitem.setProperty("agiAddlColumn3", agiAddlColumn3);
        	agendaitem.setProperty("agiAddlColumn4", agiAddlColumn4);
        	agendaitem.setProperty("agiAddlColumn5", agiAddlColumn5);
        	
        	agendaitem.setProperty("agiAndroidCreated", agiAndroidCreated);
        	agendaitem.setProperty("agiOtherCreated", agiOtherCreated);
        	agendaitem.setProperty("agiWebCreated", agiWebCreated);
        	agendaitem.setProperty("agiactionItems", agiactionItems);
        	agendaitem.setProperty("agiactualEnd", agiactualEnd);
        	
        	agendaitem.setProperty("agiactualStart", agiactualStart);
        	agendaitem.setProperty("agiagendaItems", agiagendaItems);
        	agendaitem.setProperty("agicompleted", agicompleted);
        	agendaitem.setProperty("agicreatedBy", agicreatedBy);
        	agendaitem.setProperty("agicreatedOn", agicreatedOn);
        	
        	agendaitem.setProperty("agideleted", agideleted);
        	agendaitem.setProperty("agidesc", agidesc);
        	agendaitem.setProperty("agiduration", agiduration);
        	agendaitem.setProperty("agiiPadAppCreated", agiiPadAppCreated);
        	agendaitem.setProperty("agiid", agiid);
        	
        	agendaitem.setProperty("agimeetingID", agimeetingID);
        	agendaitem.setProperty("agimeetings", agimeetings);
        	agendaitem.setProperty("agimodifiedOn", agimodifiedOn);
        	agendaitem.setProperty("aginotes", aginotes);
        	agendaitem.setProperty("agiorder", agiorder);

        	agendaitem.setProperty("agiparticipants", agiparticipants);
        	agendaitem.setProperty("agipresenter", agipresenter);
        	agendaitem.setProperty("agisyncDate", agisyncDate);
        	agendaitem.setProperty("agititle", agititle);
        	agendaitem.setProperty("agitype", agitype);  
      }
    }
    Util.persistEntity(agendaitem);
    return agendaitem;
  }

  public static Iterable<Entity> getAllAgendaitems() {
    Iterable<Entity> entities = Util.listEntities("Agendaitem", null, null);
    return entities;
  }

  public static Iterable<Entity> getAgendaitem(String agendaItemID) {
    Iterable<Entity> entities = Util.listEntities("Agendaitem", "agendaItemID", agendaItemID);
    return entities;
  }

  public static Iterable<Entity> getAgendaitemsForMeeting(String kind, String meetingID) {
    Key ancestorKey = KeyFactory.createKey("Meeting", meetingID);
    return Util.listChildren("Agendaitem", ancestorKey);
  }

  public static Entity getSingleAgendaitem(String agendaItemID) {
    Iterable<Entity> results = Util.listEntities("Agendaitem", "agendaItemID", agendaItemID);
    List<Entity> entity = new ArrayList<Entity>();
    for(Entity e : results)
      if(e!=null)
        entity.add(e);
      if (!entity.isEmpty()) {
        return (Entity)entity.remove(0);
      }
	  return null;
  }
}
