package com.rigil.meetingapp;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;




import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;


/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 */
public class WMRUtil {

	private static final Logger logger = Logger.getLogger(Util.class
			.getCanonicalName());
	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private static MemcacheService keycache = MemcacheServiceFactory
			.getMemcacheService();

	/**
	 * Search and return the entity from the cache . If absent , search the
	 * datastore.
	 * 
	 * @param key
	 *            : key to find the entity
	 * @return entity
	 */
	public static Entity findEntity(Key key) {
		logger.log(Level.INFO, "Search the entity");
		try {
			Entity entity = getFromCache(key);
			if (entity != null) {
				return entity;
			}
			return datastore.get(key);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	/***
	 * Search entities based on search criteria
	 * 
	 * @param kind
	 * @param searchBy
	 *            : Searching Criteria (Property)
	 * @param searchFor
	 *            : Searching Value (Property Value)
	 * @return List all entities of a kind from the cache or datastore (if not
	 *         in cache) with the specified properties
	 */
	public static Iterable<Entity> listEntities(String kind, String searchBy,
			String searchFor) {
		logger.log(Level.INFO, "Search entities based on search criteria");
		logger.log(Level.INFO, "" + searchFor + "  ");
		Query query = new Query(kind);
		
		if (searchFor != null && !"".equals(searchFor)) {
			query.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
		}
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();

	}

	/**
	 * Get the list of children from a parent key in the entity group
	 * 
	 * @param kind
	 *            : the entity kind of the children that is to be searched for
	 * @param ancestor
	 *            : the parent key of the entity group where we need to search
	 * @return iterable with all children of the parent of the specified kind
	 */
	public static Iterable<Entity> listChildren(String kind, Key ancestor) {
		logger.log(Level.INFO, "Search entities based on parent");
		Query query = new Query(kind);
		query.setAncestor(ancestor);
		query.addFilter(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.GREATER_THAN, ancestor);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}

	public static Iterable<Entity> listChildrenSearch(String kind,
			String searchBy, String searchFor, String ancestor) {
		logger.log(Level.INFO, "listChildrenSearch");
		Query query = new Query(kind);
		
		if (searchBy != null && !"".equals(searchBy)) {
			query.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
		}
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}

	/**
	 * Get the list of keys of all children for a given entity kind in a given
	 * entity group represented by the parent key
	 * 
	 * @param kind
	 *            : Entity kind of the children that needs to be searched for
	 * @param ancestor
	 *            : Parent key of the entity group that needs to be searched for
	 * @return an iterable with keys of children
	 */
	public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
		logger.log(Level.INFO, "Search entities based on parent");
		Query query = new Query(kind);
		query.setAncestor(ancestor).setKeysOnly();
		query.addFilter(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.GREATER_THAN, ancestor);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}

	/**
	 * List the entities in JSON format
	 * 
	 * @param entities
	 *            entities to return as JSON strings
	 */
	public static String writeJSON(Iterable<Entity> entities) {
		logger.log(Level.INFO, "creating JSON format object");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		
		for (Entity result : entities) {
			Map<String, Object> properties = result.getProperties();
			sb.append("{");
			if (result.getKey().getName() == null)
				sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
			else
				sb.append("\"name\" : \"" + result.getKey().getName() + "\",");

			for (String key : properties.keySet()) {
				sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},");
			i++;
		}
		if (i > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		
		
		
		return sb.toString();
	}

	public static String writeJSONWithTitle(Iterable<Entity> entities, String JSONTitle) {
		logger.log(Level.INFO, "writeJSONWithTitle - creating JSON format object");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		
		
		  sb.append("{\"" +
		 
				"mcreatedBy" +
				"\": [");
		
		for (Entity result : entities) {
			Map<String, Object> properties = result.getProperties();
			sb.append("{");
			if (result.getKey().getName() == null)
				sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
			else
				sb.append("\"name\" : \"" + result.getKey().getName() + "\",");

			for (String key : properties.keySet()) {
				sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},");
			i++;
		}
		if (i > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		
		sb.append("]}");
		logger.log(Level.INFO,""+sb+"  ");
		return sb.toString();
	}
	/**
	 * 
	 * Retrieves Parent and Child entities into JSON String
	 * 
	 * @param entities
	 *            : List of parent entities
	 * @param childKind
	 *            : Entity type for Child
	 * @param fkName
	 *            : foreign-key to the parent in the child entity
	 * @return JSON string
	 */
	public static String writeJSON(Iterable<Entity> entities, String childKind,
			String fkName) {
		logger.log(Level.INFO,
				"creating JSON format object for parent child relation");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("{\"data\": [");
		for (Entity result : entities) {
			Map<String, Object> properties = result.getProperties();
			sb.append("{");
			if (result.getKey().getName() == null) {
				sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
			} else {
				sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
			}
			for (String key : properties.keySet()) {
				sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
				if (key.equals("mparticipantsString")) {
					String pstr = String.valueOf(properties.get(key));
					String[] ptemp;
					String pdelimiter = ";";
					ptemp = pstr.split(pdelimiter);
					for (int psi = 0; psi < ptemp.length; psi++) {
						if (ptemp[psi] != null) {
							Iterable<Entity> pschild = listEntities(
									"Directory", "contactID", ptemp[psi]);
							for (Entity psen : pschild) {
								for (String pskey : psen.getProperties()
										.keySet()) {
									sb.append("\"" + pskey + "\" : \""
											+ psen.getProperties().get(pskey)
											+ "\",");
								}
							}
						}
					}
				}
			}
			Iterable<Entity> child = listEntities(childKind, fkName,
					String.valueOf(result.getKey().getName()));
			for (Entity en : child) {
				for (String key : en.getProperties().keySet()) {
					sb.append("\"" + key + "\" : \""
							+ en.getProperties().get(key) + "\",");
				}
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},");
			i++;
		}
		if (i > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]}");
		return sb.toString();
	}

	public static String writeEntityJSON(Entity entity, String childKind,
			String fkName) {

		logger.log(Level.INFO,
				"creating JSON format object for ONE OBJECT parent child relation");
		StringBuilder sbmreport = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbdl = new StringBuilder();
		StringBuilder sbagl = new StringBuilder();
		StringBuilder sbmrtemp = new StringBuilder();
		int i = 0;
		int j = 0;
		int k = 0;
		sbmreport.append("{\"data\": [");
		
		Map<String, Object> properties = entity.getProperties();
		sb.append("{");
		if (entity.getKey().getName() == null) {
			sb.append("\"name\" : \"" + entity.getKey().getId() + "\",");
		} else {
			sb.append("\"name\" : \"" + entity.getKey().getName() + "\",");
		}

		for (String key : properties.keySet()) {
			
			sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
			
			if (key.equals("mparticipantsString")) {
				String pstr = String.valueOf(properties.get(key));
				String[] ptemp;
				String pdelimiter = ";";
				ptemp = pstr.split(pdelimiter);
				sbdl.append(",");
				for (int psi = 0; psi < ptemp.length; psi++) {
					sbdl.append("{");
					if (ptemp[psi] != null) {
						Iterable<Entity> pschild = listEntities("Directory",
								"contactID", ptemp[psi]);

						for (Entity psen : pschild) {
							for (String pskey : psen.getProperties().keySet()) {

								sbdl.append("\"" + pskey + "\" : \""
										+ psen.getProperties().get(pskey)
										+ "\",");
							}
							j++;
							if (j > 0) {
								sbdl.deleteCharAt(sbdl.lastIndexOf(","));
							}
						}
					}
					sbdl.append("},");
				}
				;
				sbdl.deleteCharAt(sbdl.lastIndexOf(","));
			}
		}
		
		Iterable<Entity> child = listEntities(childKind, fkName,
				String.valueOf(entity.getKey().getName()));
		sb.append("}");
		sbagl.append(",");
		for (Entity en : child) {
			sbagl.append("{");
			for (String childkey : en.getProperties().keySet()) {
				sbagl.append("\"" + childkey + "\" : \""
						+ en.getProperties().get(childkey) + "\",");
			}
			k++;
			if (k > 0) {
				sbagl.deleteCharAt(sbagl.lastIndexOf(","));
			}
			sbagl.append("},");
		}

		sbagl.deleteCharAt(sbagl.lastIndexOf(","));
		

		
		
		
		i++;
		if (i > 0) {
			logger.log(Level.INFO, "" + i + "  ");
			sb.deleteCharAt(sb.lastIndexOf(","));
		}

		
		sbmreport = sbmreport.append(sb);
		sbmreport = sbmreport.append(sbagl);

		sbmreport = sbmreport.append(sbdl);

		sbmreport.append("]}");
		
		logger.log(Level.INFO, "" + sbmreport + "  ");
		
		
		

		return sbmreport.toString();

	}

	public static String writeReportEntityJSON(Entity entity,
			String childKindagi, String childKindn, String childKindaci,
			String childKinda, String fkName, String nfkName, String acifkName,
			String afkName) {
		logger.log(Level.INFO,
				"MEReport - creating JSON format object for ONE OBJECT parent child relation");
		StringBuilder sbmreport = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		StringBuilder sbdl = new StringBuilder();
		StringBuilder sbagl = new StringBuilder();
		StringBuilder sbmnl = new StringBuilder();
		StringBuilder sbmacl = new StringBuilder();
		StringBuilder sbmal = new StringBuilder();
		StringBuilder sbmrtemp = new StringBuilder();
		int i = 0;
		int j = 0;
		int k = 0;
		int mnl = 0;
		
		
		sbmreport.append("{\"data\": [{");
		
		logger.log(Level.INFO,""+entity+"  ");
		Map<String, Object> properties = entity.getProperties();
		
		if (entity.getKey().getName() == null) {
			sb.append("\"name\" : \"" + entity.getKey().getId() + "\",");
		} else {
			sb.append("\"name\" : \"" + entity.getKey().getName() + "\",");
		}
		
		for (String key : properties.keySet()) {
			
			sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
			
			if (key.equals("mparticipantsString")) {
				String pstr = String.valueOf(properties.get(key));
				String[] ptemp;
				String pdelimiter = ";";
				ptemp = pstr.split(pdelimiter);
				
				for (int psi = 0; psi < ptemp.length; psi++) {
					
					if (ptemp[psi] != null) {
						Iterable<Entity> pschild = listEntities("Directory",
								"contactID", ptemp[psi]);

						for (Entity psen : pschild) {
							sbdl.append("{");
							for (String pskey : psen.getProperties().keySet()) {

								sbdl.append("\"" + pskey + "\" : \""
										+ psen.getProperties().get(pskey)
										+ "\",");
							}
							j++;
							if (j > 0) {
								sbdl.deleteCharAt(sbdl.lastIndexOf(","));
							}
							sbdl.append("},");
						}
					}
				
				}
				;
				
			}
		}
		

		
		Iterable<Entity> child = listEntities(childKindagi, fkName,
				String.valueOf(entity.getKey().getName()));
		
		sbagl.append(",");
		for (Entity en : child) {
			sbagl.append("{");
			for (String childkey : en.getProperties().keySet()) {
				sbagl.append("\"" + childkey + "\" : \""
						+ en.getProperties().get(childkey) + "\",");
			}
			k++;
			if (k > 0) {
				sbagl.deleteCharAt(sbagl.lastIndexOf(","));
			}

			sbagl.append("},");
		}

		sbagl.deleteCharAt(sbagl.lastIndexOf(","));
		

		
		
		
		i++;
		if (i > 0) {
			logger.log(Level.INFO, "" + i + "  ");
			sb.deleteCharAt(sb.lastIndexOf(","));
		}

		
		sbmreport = sbmreport.append(sb);
		
		
		

		if (sbdl.length() > 0) {
			sbmreport = sbmreport.append("},");
			sbdl.deleteCharAt(sbdl.lastIndexOf(","));
			sbmreport = sbmreport.append(sbdl);
		} else {
			sbmreport = sbmreport.append("}");
		}
		
		logger.log(Level.INFO, "sb" + sb + "  ");
		logger.log(Level.INFO, "sbdl" + sbdl + "  ");
		
		
		
		
		
		

		
		
		
		
		
		
		

		
/*
		logger.log(Level.INFO, "" + childKindn + "  ");
		logger.log(Level.INFO, "" + nfkName + "  ");
		logger.log(Level.INFO, "" + childKindagi + "  ");
		logger.log(Level.INFO, "" + fkName + "  ");
		logger.log(Level.INFO, "" + childKindaci + "  ");
		logger.log(Level.INFO, "" + acifkName + "  "); */
		
		
		
		
		
		String MNotesJSON = writeNotesJSON(entity, childKindn, nfkName);
		if (MNotesJSON.length() > 0) {
			sbmreport.append(",");
			sbmreport = sbmreport.append(MNotesJSON);
		}

		
		
		
/*
		String AgNotesJSON = writeAgAcNotesJSON1(entity, childKindagi, fkName);
		if (AgNotesJSON.length() > 0) {
			sbmreport.append(",");
			sbmreport = sbmreport.append(AgNotesJSON);
			sbmreport.deleteCharAt(sbmreport.lastIndexOf(","));
		}
*/
		StringBuilder AgNotesJSON = new StringBuilder();
		String AgNotesJSONRetrieve = writeAgAcNotesJSON(entity, childKindagi, fkName);
		AgNotesJSON.append(AgNotesJSONRetrieve);
		if (AgNotesJSON.length() > 0) {
			sbmreport.append(",");
			AgNotesJSON.deleteCharAt(AgNotesJSON.lastIndexOf(","));
			sbmreport = sbmreport.append(AgNotesJSON);
		}

		
		

		StringBuilder AcNotesJSON = new StringBuilder();
		
		String AcNotesJSONRetrieve = writeAcAgNotesJSON(entity, childKindaci, acifkName);
		AcNotesJSON.append(AcNotesJSONRetrieve);
		if (AcNotesJSON.length() > 0) {
			sbmreport.append(",");
			AcNotesJSON.deleteCharAt(AcNotesJSON.lastIndexOf(","));
			sbmreport = sbmreport.append(AcNotesJSON);
		}

		
		

		

		
		
		
		
		
		
		
		sbmreport.append("]}");
		
		
		
		
		
		
		logger.log(Level.INFO, "" + sbmreport + "");
		return sbmreport.toString();
		
	}

	
	public static String writeNotesJSON(Entity entity, String childKindn,
			String nfkName) {
		
		
		
		logger.log(Level.INFO, "creating Notes JSON format---*---");
		StringBuilder wNJreport = new StringBuilder();
		StringBuilder sbwNJ = new StringBuilder();
		int wNJ = 0;
		Iterable<Entity> nchild = listEntities(childKindn, nfkName,
				String.valueOf(entity.getKey().getName()));
		
		
		for (Entity nen : nchild) {
			
			
	        
	        
			
	        
			sbwNJ.append("{");
			for (String nchildkey : nen.getProperties().keySet()) {
				sbwNJ.append("\"" + nchildkey + "\" : \""
						+ nen.getProperties().get(nchildkey) + "\",");
				
		        
		        
		        
			}
			wNJ++;
			if (wNJ > 0) {
				sbwNJ.deleteCharAt(sbwNJ.lastIndexOf(","));
			}
			sbwNJ.append("},");
		}
		if (wNJ > 0) {
			sbwNJ.deleteCharAt(sbwNJ.lastIndexOf(","));
		}
		
		

		return sbwNJ.toString();

	}

	
	public static String writeAcAgNotesJSON(Entity entity, String childKindn,
			String nfkName) {
		logger.log(Level.INFO, "creating Ac/AgNotes JSON format---$---");
		StringBuilder wAcAgNJSON = new StringBuilder();
		StringBuilder wAcAgNJreport = new StringBuilder();
		int wAcIDForN = 0;
		int wAcIDForN1 = 0;
		StringBuilder AcNotesList = new StringBuilder();
		String childequals = "";

		StringBuilder sbwAcAgNJ = new StringBuilder();
		StringBuilder sbwAcAgNJNested = new StringBuilder();
		StringBuilder sbwAcAgNJ2 = new StringBuilder();
		int wAcAgNJ = 0;
		Iterable<Entity> nchild = listEntities(childKindn, nfkName,
				String.valueOf(entity.getKey().getName()));
	
		if (childKindn.equals("Actionitem")) { 	childequals = "actionItemID"; }

		for (Entity nen : nchild) {
			sbwAcAgNJ.append("{");
			
			for (String nchildkey : nen.getProperties().keySet()) {
				sbwAcAgNJ.append("\"" + nchildkey + "\" : \""
						+ nen.getProperties().get(nchildkey) + "\",");
				if (nchildkey.equals(childequals)) {
					String AcIDForNotes = String.valueOf(nen.getProperties()
							.get(nchildkey));
				
				
					if (AcIDForNotes != null) {
						Iterable<Entity> AcForNoteschild = listEntities("Note",
								"nparentID", AcIDForNotes);
						for (Entity AcIDForNotesen : AcForNoteschild) {
							sbwAcAgNJNested.append("{");
							for (String AcForNoteskey : AcIDForNotesen
									.getProperties().keySet()) {
							sbwAcAgNJNested.append("\""
										+ AcForNoteskey
										+ "\" : \""
										+ AcIDForNotesen.getProperties().get(
												AcForNoteskey) + "\",");
								wAcIDForN++;
							}
							if (wAcIDForN > 1) {
								sbwAcAgNJNested.deleteCharAt(sbwAcAgNJNested
										.lastIndexOf(","));
							}
							sbwAcAgNJNested.append("},");
						}
					}
					wAcIDForN = 0;
				};
				wAcIDForN1++;
				wAcAgNJ++;
				
				

			}
			
			if (wAcAgNJ > 0) {
				sbwAcAgNJ.deleteCharAt(sbwAcAgNJ.lastIndexOf(","));
			}
			sbwAcAgNJ.append("},");
			if (sbwAcAgNJNested != null) {
				sbwAcAgNJ = sbwAcAgNJ.append(sbwAcAgNJNested);
				sbwAcAgNJNested.setLength(0);
			}
			
			
				
			
		}

		return sbwAcAgNJ.toString();
	}


	
	public static String writeAgAcNotesJSON1(Entity entity, String childKindn,
			String nfkName) {
		logger.log(Level.INFO, "creating Ag/AcNotes JSON format---$---");
		StringBuilder wAgAcNJSON = new StringBuilder();
		StringBuilder wAgAcNJHeading1 = new StringBuilder();
		StringBuilder wAgAcNJHeading2 = new StringBuilder();
		StringBuilder wAgAcNJreport = new StringBuilder();
		int wAgIDForN = 0;
		int wAgIDForN1 = 0;
		StringBuilder AgNotesList = new StringBuilder();
		String childequals = "";

		StringBuilder sbwAgAcNJ = new StringBuilder();
		StringBuilder sbwAgAcNJNested = new StringBuilder();
		StringBuilder sbwAgAcNJ2 = new StringBuilder();
		int wAgAcNJ = 0;
		Iterable<Entity> nchild = listEntities(childKindn, nfkName,
				String.valueOf(entity.getKey().getName()));
		
		
		
		if (childKindn.equals("Agendaitem")) {
			
			
			
			childequals = "ID";
		} else if (childKindn.equals("Actionitem")) {
			
			
			childequals = "actionItemID";
		}

		for (Entity nen : nchild) {
			
		
			sbwAgAcNJ.append("{");
			
			for (String nchildkey : nen.getProperties().keySet()) {
				sbwAgAcNJ.append("\"" + nchildkey + "\" : \""
						+ nen.getProperties().get(nchildkey) + "\",");
				
				
				if (nchildkey.equals(childequals)) {
					
					
					
					
					String AgIDForNotes = String.valueOf(nen.getProperties()
							.get(nchildkey));
					sbwAgAcNJ.append("}");
					

					sbwAgAcNJNested.append(",");
					if (AgIDForNotes != null) {
						Iterable<Entity> AgForNoteschild = listEntities("Note",
								"nparentID", AgIDForNotes);
						for (Entity AgIDForNotesen : AgForNoteschild) {
							sbwAgAcNJNested.append("{");
							for (String AgForNoteskey : AgIDForNotesen
									.getProperties().keySet()) {
								
							sbwAgAcNJNested.append("\""
										+ AgForNoteskey
										+ "\" : \""
										+ AgIDForNotesen.getProperties().get(
												AgForNoteskey) + "\",");
								wAgIDForN++;
								
							}
							if (wAgIDForN > 1) {
								sbwAgAcNJNested.deleteCharAt(sbwAgAcNJNested
										.lastIndexOf(","));
							}
							
							sbwAgAcNJNested.append("},");

						}
						
					}
					wAgIDForN = 0;
					
					
					
					

				};
				

				
				wAgIDForN1++;
			}
			wAgAcNJ++;
			if (wAgAcNJ > 0) {
				sbwAgAcNJ.deleteCharAt(sbwAgAcNJ.lastIndexOf(","));
			}
			
			
				
			
			
			
			if (sbwAgAcNJNested != null) {
				
				sbwAgAcNJ = sbwAgAcNJ.append(sbwAgAcNJNested);
				sbwAgAcNJNested.setLength(0);

			}
			else if (sbwAgAcNJNested == null)
			{
				wAgIDForN1++;
				
			}

			
			

			wAgAcNJHeading1 = wAgAcNJHeading1.append("]},");
			wAgAcNJHeading2 = wAgAcNJHeading2.append("]},");

			
			
			 
			 
			
			
		}

		if (wAgAcNJ > 0) {
			
			sbwAgAcNJ.deleteCharAt(sbwAgAcNJ.lastIndexOf(","));
			
		}
		
		
		
		
		wAgAcNJSON = wAgAcNJSON.append(wAgAcNJHeading1);
		wAgAcNJSON = wAgAcNJSON.append(wAgAcNJHeading2);

		return sbwAgAcNJ.toString();

	}

	
	
		public static String writeAgAcNotesJSON(Entity entity, String childKindn,
				String nfkName) {
			logger.log(Level.INFO, "creating Ag/AcNotes JSON format---$---");
			StringBuilder wAcAgNJSON = new StringBuilder();
			StringBuilder wAcAgNJreport = new StringBuilder();
			int wAcIDForN = 0;
			int wAcIDForN1 = 0;
			StringBuilder AcNotesList = new StringBuilder();
			String childequals = "";

			StringBuilder sbwAcAgNJ = new StringBuilder();
			StringBuilder sbwAcAgNJNested = new StringBuilder();
			StringBuilder sbwAcAgNJ2 = new StringBuilder();
			int wAcAgNJ = 0;
			Iterable<Entity> nchild = listEntities(childKindn, nfkName,
					String.valueOf(entity.getKey().getName()));
		
			if (childKindn.equals("Agendaitem")) { 	childequals = "agendaItemID"; }

			for (Entity nen : nchild) {
				sbwAcAgNJ.append("{");
				
				for (String nchildkey : nen.getProperties().keySet()) {
					sbwAcAgNJ.append("\"" + nchildkey + "\" : \""
							+ nen.getProperties().get(nchildkey) + "\",");
					if (nchildkey.equals(childequals)) {
						String AcIDForNotes = String.valueOf(nen.getProperties()
								.get(nchildkey));
					
					
						if (AcIDForNotes != null) {
							Iterable<Entity> AcForNoteschild = listEntities("Note",
									"nparentID", AcIDForNotes);
							for (Entity AcIDForNotesen : AcForNoteschild) {
								sbwAcAgNJNested.append("{");
								for (String AcForNoteskey : AcIDForNotesen
										.getProperties().keySet()) {
								sbwAcAgNJNested.append("\""
											+ AcForNoteskey
											+ "\" : \""
											+ AcIDForNotesen.getProperties().get(
													AcForNoteskey) + "\",");
									wAcIDForN++;
								}
								if (wAcIDForN > 1) {
									sbwAcAgNJNested.deleteCharAt(sbwAcAgNJNested
											.lastIndexOf(","));
								}
								sbwAcAgNJNested.append("},");
							}
						}
						wAcIDForN = 0;
					};
					wAcIDForN1++;
					wAcAgNJ++;
					
					

				}
				
				if (wAcAgNJ > 0) {
					sbwAcAgNJ.deleteCharAt(sbwAcAgNJ.lastIndexOf(","));
				}
				sbwAcAgNJ.append("},");
				if (sbwAcAgNJNested != null) {
					sbwAcAgNJ = sbwAcAgNJ.append(sbwAcAgNJNested);
					sbwAcAgNJNested.setLength(0);
				}
				
				
					
				
			}

			return sbwAcAgNJ.toString();
		}

	
	
	
	public static String writeAcNotesJSON(Entity entity, String childKindn,
			String nfkName) {
		logger.log(Level.INFO, "creating Notes JSON format---*---");
		StringBuilder wNJreport = new StringBuilder();
		StringBuilder sbwNJ = new StringBuilder();
		int wNJ = 0;
		Iterable<Entity> nchild = listEntities(childKindn, nfkName,
				String.valueOf(entity.getKey().getName()));
		
		
		for (Entity nen : nchild) {
			sbwNJ.append("{");
			for (String nchildkey : nen.getProperties().keySet()) {
				sbwNJ.append("\"" + nchildkey + "\" : \""
						+ nen.getProperties().get(nchildkey) + "\",");
			}
			wNJ++;
			if (wNJ > 0) {
				sbwNJ.deleteCharAt(sbwNJ.lastIndexOf(","));
			}
			sbwNJ.append("},");
		}
		if (wNJ > 0) {
			sbwNJ.deleteCharAt(sbwNJ.lastIndexOf(","));
		}
		
		

		return sbwNJ.toString();

	}

	/**
	 * Adds the entity to cache
	 * 
	 * @param key
	 *            : key of the entity
	 * @param entity
	 *            : Entity being added
	 */
	public static void addToCache(Key key, Entity entity) {
		logger.log(Level.INFO, "Adding entity to cache");
		keycache.put(key, entity);
	}

	/**
	 * Delete the entity from cache
	 * 
	 * @param key
	 *            : Key of the entity that needs to be deleted
	 */
	public static void deleteFromCache(Key key) {
		logger.log(Level.INFO, "Deleting entity from cache");
		keycache.delete(key);
	}

	/**
	 * Delete entities based on a set of keys
	 * 
	 * @param keys
	 *            : list of keys for the entities that are to be deleted
	 */
	public static void deleteFromCache(List<Key> keys) {
		keycache.deleteAll(keys);
	}

	/**
	 * Search for an entity based on key in the cache
	 * 
	 * @param key
	 *            : key of the entity that is searched for
	 * @return the entity
	 */
	public static Entity getFromCache(Key key) {
		logger.log(Level.INFO, "Searching entity in cache");
		return (Entity) keycache.get(key);
	}

	/**
	 * Utility method to send the error back to UI
	 * 
	 * @param data
	 * @param resp
	 * @throws IOException
	 */
	public static String getErrorResponse(Exception ex) throws IOException {
		return "Error:" + ex.toString();
	}

	/**
	 * Utility method to get the datastore service in entities
	 * 
	 * @return datastore
	 */
	public static DatastoreService getDatastoreServiceInstance() {
		return datastore;
	}
}