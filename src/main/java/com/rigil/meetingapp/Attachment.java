package com.rigil.meetingapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Attachment {

 public static Entity createOrUpdateAttachment(String attachmentID, Date acreatedOn, Boolean adeleted,
		 String adesc, String afileName, Date amodifiedOn,
		 String atype, String anoteID, String aparentType, String aparentID) {
 	 
    Entity meeting = Meeting.getMeeting(aparentID);
    Entity attachment = getSingleAttachment(attachmentID);
    if (attachment == null) {

    	attachment = new Entity("Attachment", attachmentID);
    	attachment.setProperty("attachmentID", attachmentID);
    	attachment.setProperty("acreatedOn", acreatedOn);
    	attachment.setProperty("adeleted", adeleted);
    	attachment.setProperty("adesc", adesc);
    	attachment.setProperty("afileName", afileName);
    	attachment.setProperty("amodifiedOn", amodifiedOn);
    	attachment.setProperty("atype", atype);
    	attachment.setProperty("anoteID", anoteID);
    	attachment.setProperty("aparentType", aparentType);
    	attachment.setProperty("aparentID", aparentID);
    } else {
      if (attachment != null && !"".equals(attachment)) {
    	  attachment.setProperty("attachmentID", attachmentID);
    	  attachment.setProperty("acreatedOn", acreatedOn);
    	  attachment.setProperty("adeleted", adeleted);
    	  attachment.setProperty("adesc", adesc);
    	  attachment.setProperty("afileName", afileName);
    	  attachment.setProperty("amodifiedOn", amodifiedOn);
    	  attachment.setProperty("atype", atype);
    	  attachment.setProperty("anoteID", anoteID);
      	attachment.setProperty("aparentType", aparentType);
      	attachment.setProperty("aparentID", aparentID);
      }
    }
    Util.persistEntity(attachment);
    return attachment;
  }

  public static Iterable<Entity> getAllAttachments() {
    Iterable<Entity> entities = Util.listEntities("Attachment", null, null);
    return entities;
  }

  public static Iterable<Entity> getAttachment(String attachmentID) {
    Iterable<Entity> entities = Util.listEntities("Attachment", "attachmentID", attachmentID);
    return entities;
  }

  public static Iterable<Entity> getAttachmentsForMeeting(String kind, String meetingID) {
    Key ancestorKey = KeyFactory.createKey("Meeting", meetingID);
    return Util.listChildren("Attachment", ancestorKey);
  }

  public static Entity getSingleAttachment(String attachmentID) {
    Iterable<Entity> results = Util.listEntities("Attachment", "attachmentID", attachmentID);
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
