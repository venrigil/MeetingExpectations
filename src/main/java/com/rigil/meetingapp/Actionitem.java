package com.rigil.meetingapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Actionitem {

 public static Entity createOrUpdateActionitem(
		 String actionItemID, String aciassignee, String acidesc, Boolean acideleted, 
		 String acimeetingID, Date acicreatedOn, Date acimodifiedOn, Date acisyncDate, 
		 String acistatus, Integer aciorder, Date acidueDate, String acimeetings, 
		 String aciagendaItems, String aciparticipants, String acinotes, 
		 String aciactionItems, String aciWebCreated, String aciiPadAppCreated, 
		 String aciAndroidCreated, String aciOtherCreated, String aciAddlColumn1, 
		 String aciAddlColumn2, String aciAddlColumn3, String aciAddlColumn4, 
		 String aciAddlColumn5, String acicreatedBy, String aciparentType, String aciparentID) {
 	 
    Entity meeting = Meeting.getMeeting(acimeetingID);
    
    Entity actionitem = getSingleActionitem(actionItemID);
    if (actionitem == null) {

    	actionitem = new Entity("Actionitem", actionItemID);
    	actionitem.setProperty("actionItemID", actionItemID);
    	actionitem.setProperty("aciassignee", aciassignee);
    	actionitem.setProperty("acidesc", acidesc);
    	actionitem.setProperty("acideleted", acideleted);
    	actionitem.setProperty("acimeetingID", acimeetingID);
    	actionitem.setProperty("acicreatedOn", acicreatedOn);
    	actionitem.setProperty("acimodifiedOn", acimodifiedOn);
    	actionitem.setProperty("acisyncDate", acisyncDate);
    	actionitem.setProperty("acistatus", acistatus);
    	actionitem.setProperty("aciorder", aciorder);
    	actionitem.setProperty("acidueDate", acidueDate);
    	actionitem.setProperty("acimeetings", acimeetings);
    	actionitem.setProperty("aciagendaItems", aciagendaItems);
    	actionitem.setProperty("aciparticipants", aciparticipants);
    	actionitem.setProperty("acinotes", acinotes);
    	actionitem.setProperty("aciactionItems", aciactionItems);
    	actionitem.setProperty("aciiPadAppCreated", aciiPadAppCreated);
    	actionitem.setProperty("aciAndroidCreated", aciAndroidCreated);
    	actionitem.setProperty("aciOtherCreated", aciOtherCreated);
    	actionitem.setProperty("aciOtherCreated", aciOtherCreated);
    	actionitem.setProperty("aciAddlColumn1", aciAddlColumn1);
    	actionitem.setProperty("aciAddlColumn2", aciAddlColumn2);
    	actionitem.setProperty("aciAddlColumn3", aciAddlColumn3);
    	actionitem.setProperty("aciAddlColumn4", aciAddlColumn4);
    	actionitem.setProperty("aciAddlColumn5", aciAddlColumn5);
    	actionitem.setProperty("acicreatedBy", acicreatedBy);
    	actionitem.setProperty("aciparentType", aciparentType);    
    	actionitem.setProperty("aciparentID", aciparentID);    
    } else {
      if (actionitem != null && !"".equals(actionitem)) {
       
    	  actionitem.setProperty("actionItemID", actionItemID);
    	  actionitem.setProperty("aciassignee", aciassignee);
    	  actionitem.setProperty("acidesc", acidesc);
    	  actionitem.setProperty("acideleted", acideleted);
    	  actionitem.setProperty("acimeetingID", acimeetingID);
    	  actionitem.setProperty("acicreatedOn", acicreatedOn);
    	  actionitem.setProperty("acimodifiedOn", acimodifiedOn);
    	  actionitem.setProperty("acisyncDate", acisyncDate);
    	  actionitem.setProperty("acistatus", acistatus);
    	  actionitem.setProperty("aciorder", aciorder);
    	  actionitem.setProperty("acidueDate", acidueDate);
    	  actionitem.setProperty("acimeetings", acimeetings);
    	  actionitem.setProperty("aciagendaItems", aciagendaItems);
    	  actionitem.setProperty("aciparticipants", aciparticipants);
    	  actionitem.setProperty("acinotes", acinotes);
    	  actionitem.setProperty("aciactionItems", aciactionItems);
    	  actionitem.setProperty("aciiPadAppCreated", aciiPadAppCreated);
    	  actionitem.setProperty("aciAndroidCreated", aciAndroidCreated);
    	  actionitem.setProperty("aciOtherCreated", aciOtherCreated);
    	  actionitem.setProperty("aciOtherCreated", aciOtherCreated);
    	  actionitem.setProperty("aciAddlColumn1", aciAddlColumn1);
    	  actionitem.setProperty("aciAddlColumn2", aciAddlColumn2);
    	  actionitem.setProperty("aciAddlColumn3", aciAddlColumn3);
    	  actionitem.setProperty("aciAddlColumn4", aciAddlColumn4);
    	  actionitem.setProperty("aciAddlColumn5", aciAddlColumn5);
    	  actionitem.setProperty("acicreatedBy", acicreatedBy);
      	  actionitem.setProperty("aciparentType", aciparentType);    
      	  actionitem.setProperty("aciparentID", aciparentID);  
      }
    }
    Util.persistEntity(actionitem);
    return actionitem;
  }

  public static Iterable<Entity> getAllActionitems() {
    Iterable<Entity> entities = Util.listEntities("Actionitem", null, null);
    return entities;
  }

  public static Iterable<Entity> getActionitem(String actionItemID) {
    Iterable<Entity> entities = Util.listEntities("Actionitem", "actionItemID", actionItemID);
    return entities;
  }

  public static Iterable<Entity> getActionitemsForMeeting(String kind, String meetingID) {
    Key ancestorKey = KeyFactory.createKey("Meeting", meetingID);
    return Util.listChildren("Actionitem", ancestorKey);
  }

  public static Entity getSingleActionitem(String actionItemID) {
    Iterable<Entity> results = Util.listEntities("Actionitem", "actionItemID", actionItemID);
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
