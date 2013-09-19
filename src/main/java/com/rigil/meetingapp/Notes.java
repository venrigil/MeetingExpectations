package com.rigil.meetingapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Notes {

 
public static Entity createOrUpdateNote(String ntext, String ncreatedBy, String noteID,
		Date ncreatedOn, Date nmodifiedOn, Date nsyncDate, 
		String ntitle, String nparentType, String nparentID, String nmaaID, 
		Boolean ndeleted, String nmeetings, String nagendaItems, String nparticipants,
		String nnotes, String nactionItems, String nWebCreated, String niPadAppCreated, 
		String nAndroidCreated, String nOtherCreated, String nAddlColumn1, 
		String nAddlColumn2, String nAddlColumn3, String nAddlColumn4, String nAddlColumn5) 
	{
 	    
    Entity meeting = Meeting.getMeeting(nparentID);
    Entity note = getSingleNote(noteID);
    if (note == null) {
    	note = new Entity("Note", noteID);
    	note.setProperty("ntext", ntext);
    	note.setProperty("ncreatedBy", ncreatedBy);
    	note.setProperty("noteID", noteID);
    	note.setProperty("ncreatedOn", ncreatedOn);
    	note.setProperty("nmodifiedOn", nmodifiedOn);
    	note.setProperty("nsyncDate", nsyncDate);
    	note.setProperty("ntitle", ntitle);
    	note.setProperty("nparentType", nparentType);
    	note.setProperty("nparentID", nparentID);
    	note.setProperty("nmaaID", nmaaID);
    	note.setProperty("ndeleted", ndeleted);
    	note.setProperty("nmeetings", nmeetings);
    	note.setProperty("nagendaItems", nagendaItems);
    	note.setProperty("nparticipants", nparticipants);
    	note.setProperty("nnotes", nnotes);
    	note.setProperty("nactionItems", nactionItems);
    	note.setProperty("nWebCreated", nWebCreated);
    	note.setProperty("niPadAppCreated", niPadAppCreated);
    	note.setProperty("nAndroidCreated", nAndroidCreated);
    	note.setProperty("nOtherCreated", nOtherCreated);
    	note.setProperty("nAddlColumn1", nAddlColumn1);
    	note.setProperty("nAddlColumn2", nAddlColumn2);
    	note.setProperty("nAddlColumn3", nAddlColumn3);
    	note.setProperty("nAddlColumn4", nAddlColumn4);
    	note.setProperty("nAddlColumn5", nAddlColumn5);
    } else {
      if (note != null && !"".equals(note)) {
      	note.setProperty("ntext", ntext);
      	note.setProperty("ncreatedBy", ncreatedBy);
      	note.setProperty("noteID", noteID);
      	note.setProperty("ncreatedOn", ncreatedOn);
      	note.setProperty("nmodifiedOn", nmodifiedOn);
      	note.setProperty("nsyncDate", nsyncDate);
      	note.setProperty("ntitle", ntitle);
      	note.setProperty("nparentType", nparentType);
      	note.setProperty("nparentID", nparentID);
      	note.setProperty("nmaaID", nmaaID);
      	note.setProperty("ndeleted", ndeleted);
      	note.setProperty("nmeetings", nmeetings);
      	note.setProperty("nagendaItems", nagendaItems);
      	note.setProperty("nparticipants", nparticipants);
      	note.setProperty("nnotes", nnotes);
      	note.setProperty("nactionItems", nactionItems);
      	note.setProperty("nWebCreated", nWebCreated);
      	note.setProperty("niPadAppCreated", niPadAppCreated);
      	note.setProperty("nAndroidCreated", nAndroidCreated);
      	note.setProperty("nOtherCreated", nOtherCreated);
      	note.setProperty("nAddlColumn1", nAddlColumn1);
      	note.setProperty("nAddlColumn2", nAddlColumn2);
      	note.setProperty("nAddlColumn3", nAddlColumn3);
      	note.setProperty("nAddlColumn4", nAddlColumn4);
      	note.setProperty("nAddlColumn5", nAddlColumn5);      }
    }
    Util.persistEntity(note);
    return note;
  }

  public static Iterable<Entity> getAllNotes() {
    Iterable<Entity> entities = Util.listEntities("Note", null, null);
    return entities;
  }

  public static Iterable<Entity> getNote(String noteID) {
    Iterable<Entity> entities = Util.listEntities("Note", "noteID", noteID);
    return entities;
  }

  public static Iterable<Entity> getNotesForMeeting(String kind, String nparentID) {
    Key ancestorKey = KeyFactory.createKey("Meeting", nparentID);
    return Util.listChildren("Note", ancestorKey);
  }

  public static Entity getSingleNote(String noteID) {
    Iterable<Entity> results = Util.listEntities("Note", "noteID", noteID);
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
