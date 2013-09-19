package com.intera.roostrap.domain;

import java.io.IOException;

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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import org.springframework.context.i18n.LocaleContextHolder;
import org.joda.time.format.DateTimeFormat;
import com.google.appengine.api.search.DateUtil;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;




import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 */
public class MESyncUtil {

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
		//logger.log(Level.INFO, "Search the entity");
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

	
	public static Entity listEntity(String kind, String searchBy,
			String searchFor) {
		
		
		
			Query query = new Query(kind);
			if (searchFor != null && !"".equals(searchFor)) {
				query.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
			}
			PreparedQuery pq = datastore.prepare(query);
			return pq.asSingleEntity();
	}
	
	
	public static Iterable<Entity> listEntitiesBysyncDateOlder(String kind, String searchBy,
			String searchFor, String midsearchBy, String midsearchFor) {
		//logger.log(Level.INFO, "Searching the listEntitiesBysyncDateOlder-Hybrid");
		
		
		
		
		
		
		
		/*
		EntityManager em = ...
				Query q = em.createQuery("SELECT x FROM Magazine x");
				List<Magazine> results = (List<Magazine>) q.getResultList();	
		*/
		
		Query query = new Query(kind);
		PreparedQuery pq;
		DateFormat formatter ; 
		Date Ddate ; 
		if (searchFor != null && !"".equals(searchFor)) {
		String string = searchFor;
		//logger.log(Level.INFO, "listEntitiesBysyncDateOlder-Hybrid-string" + string + "  ");
		try {

			formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
			Ddate = formatter.parse(string);
		   //logger.log(Level.INFO, "listEntitiesBysyncDateOlder-Hybrid-Ddate" + Ddate + "  ");
			
			
				
				
		
				
				
		   query.setFilter(Query.FilterOperator.GREATER_THAN_OR_EQUAL.of("mmodifiedOn", Ddate));
			//-
				//-
				
			
				
				





				
				
				}
			
	        catch(ParseException pe) {
	            System.out.println("ERROR: could not parse Ddate in listEntitiesBysyncDateOlder searchFor string");
	        }
		
		}
		
		pq = datastore.prepare(query);
		return pq.asIterable();

		
        
        

			
				
				
				
				
			

	}
	
	
	/***w
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
		//logger.log(Level.INFO, "Search entities based on search criteria");
		
		Query query = new Query(kind);
		
		
		if (searchFor != null && !"".equalsIgnoreCase(searchFor)) {
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
		//logger.log(Level.INFO, "Search entities based on parent");
		Query query = new Query(kind);
		query.setAncestor(ancestor);
		query.addFilter(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.GREATER_THAN, ancestor);
		PreparedQuery pq = datastore.prepare(query);
		return pq.asIterable();
	}

	public static Iterable<Entity> listChildrenSearch(String kind,
			String searchBy, String searchFor, String ancestor) {
		//logger.log(Level.INFO, "listChildrenSearch");
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
		//logger.log(Level.INFO, "Search entities based on parent");
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
		//logger.log(Level.INFO, "creating JSON format object");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("{\"data\": [");
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
		
		return sb.toString();
	}

	/**
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
		//logger.log(Level.INFO,"creating JSON format object for parent child relation");
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

		//logger.log(Level.INFO,"creating JSON format object for ONE OBJECT parent child relation");
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
				
				if (key.equals("mdate")) {


					sb.append("\"" + key + "\" : \"" + properties.get(key)  + "\",");				
				}
				else
				{
					sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");	
				}
				
				
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
			
			sb.deleteCharAt(sb.lastIndexOf(","));
		}

		
		sbmreport = sbmreport.append(sb);
		sbmreport = sbmreport.append(sbagl);

		sbmreport = sbmreport.append(sbdl);

		sbmreport.append("]}");
		
		//-
		
		
		

		return sbmreport.toString();

	}

	public static String writeReportEntityJSON(Entity entity,
			String childKindagi, String childKindn, String childKindaci,
			String childKinda, String fkName, String nfkName, String acifkName,
			String afkName) {
		//logger.log(Level.INFO,"MEReport - creating JSON format object for ONE OBJECT parent child relation");
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
		
		
		//-
		
		
		Map<String, Object> properties = entity.getProperties();
		sb.append("{");
		if (entity.getKey().getName() == null) {
			sb.append("\"name\" : \"" + entity.getKey().getId() + "\",");
		} else {
			sb.append("\"name\" : \"" + entity.getKey().getName() + "\",");
		}

		for (String key : properties.keySet()) {
			
			
			if (key.equals("mcreatedOn") || key.equals("mdate") || key.equals("mmodifiedOn") || key.equals("mplannedEndDateTime") || key.equals("mplannedStartDateTime") || key.equals("msyncDate")) {
				try {
					String mdateSDString = properties.get(key).toString();
				    long memsyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(mdateSDString).getTime();
					
				    sb.append("\"" + key + "\" : " + memsyncepoch + ",");
				    }
			        catch(ParseException pe) {
			            System.out.println("ERROR: EPOCH conversion for ME-M date conversion --- could not parse date in string");
			        }
			}
			else if (key.equals("mdeleted") || key.equals("mplannedDuration") || key.equals("mactualDuration") || key.equals("mplannedEnd") || key.equals("mplannedStart") ) {
				
				sb.append("\"" + key + "\" : " + properties.get(key) + ",");
			}
			else {
				sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");	
			}
			
			
			
			if (key.equals("mparticipantsString")) {
				String pstr = String.valueOf(properties.get(key));
				if (!pstr.isEmpty())
				{
				String[] ptemp;
				String pdelimiter = ";";
				ptemp = pstr.split(pdelimiter);
				//-
				for (int psi = 0; psi < ptemp.length; psi++) {
					sbdl.append("{");
					if (ptemp[psi] != null) {
						Iterable<Entity> pschild = listEntities("Directory",
								"contactID", ptemp[psi]);

						for (Entity psen : pschild) {
							for (String pskey : psen.getProperties().keySet()) {
								if (pskey.equals("pcreatedOn") || pskey.equals("pmodifiedOn") || pskey.equals("psyncDate")) {
									try {
										String pdateSDString = psen.getProperties().get(pskey).toString();
									    long mepsyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(pdateSDString).getTime();
									    
									    sbdl.append("\"" + pskey + "\" : " + mepsyncepoch + ",");
									    }
								        catch(ParseException pe) {
								            System.out.println("ERROR: EPOCH conversion for ME-P date conversion --- could not parse date in string");
								        }
								}
								else if (pskey.equals("pdeleted")) {
									sbdl.append("\"" + pskey + "\" : " + psen.getProperties().get(pskey) + ",");
								}
								else {
									sbdl.append("\"" + pskey + "\" : \"" + psen.getProperties().get(pskey) + "\",");	
								}
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
		}
		

		
		Iterable<Entity> child = listEntities(childKindagi, fkName,
				String.valueOf(entity.getKey().getName()));
		//-
		sbagl.append(",");
		for (Entity en : child) {
			sbagl.append("{");
			for (String childkey : en.getProperties().keySet()) {
				if (childkey.equals("agiactualEnd") || childkey.equals("agiactualStart") || childkey.equals("agicreatedOn") || childkey.equals("agimodifiedOn") || childkey.equals("agisyncDate")) {
					try {
						String agdateSDString = en.getProperties().get(childkey).toString();
					    long meagsyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(agdateSDString).getTime();
					    
					    sbagl.append("\"" + childkey + "\" : " + meagsyncepoch + ",");
					    }
				        catch(ParseException pe) {
				            System.out.println("ERROR: EPOCH conversion for ME-AG date conversion --- could not parse date in string");
				        }
				}
				else if (childkey.equals("agiduration") || childkey.equals("agiorder") || childkey.equals("agicompleted") || childkey.equals("agideleted") || childkey.equals("agiorder")) {
					sbagl.append("\"" + childkey + "\" : " + en.getProperties().get(childkey) + ",");
				}
				else {
					sbagl.append("\"" + childkey + "\" : \"" + en.getProperties().get(childkey) + "\",");	
				}

					
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
			
			sb.deleteCharAt(sb.lastIndexOf(","));
		}

		
		sbmreport = sbmreport.append(sb);
		
		
		sbmreport.append(",\"mparticipantsStringObjects\":[");
		sbmreport = sbmreport.append(sbdl);
		sbmreport.append("],");
		
		
		//-
		//-
		//-

		//-
		
		
		// -
		
		
		// -

		
/*
		logger.log(Level.INFO, "" + childKindn + "  ");
		logger.log(Level.INFO, "" + nfkName + "  ");
		logger.log(Level.INFO, "" + childKindagi + "  ");
		logger.log(Level.INFO, "" + fkName + "  ");
		logger.log(Level.INFO, "" + childKindaci + "  ");
		logger.log(Level.INFO, "" + acifkName + "  "); */
		
		
		sbmreport.append("\"mnotesObjects\":[");
		String MNotesJSON = writeNotesJSON(entity, childKindn, nfkName);
		sbmreport = sbmreport.append(MNotesJSON);
		sbmreport.append("],");
		
		String AgNotesJSON = writeAgAcNotesJSON(entity, childKindagi, fkName);
		sbmreport = sbmreport.append(AgNotesJSON);
		
		String AcNotesJSON = writeAgAcNotesJSON(entity, childKindaci, acifkName);
		sbmreport = sbmreport.append(AcNotesJSON);
		

		
		
		//-
		
		sbmreport.append("}");
		
		
		//logger.log(Level.INFO, "" + sbmreport + "  ");
		
		return sbmreport.toString();
		
	}

	
	public static String writeNotesJSON(Entity entity, String childKindn,
			String nfkName) {
		
		
		
		//logger.log(Level.INFO, "creating Notes JSON format---*---");
		StringBuilder wNJreport = new StringBuilder();
		StringBuilder sbwNJ = new StringBuilder();
		int wNJ = 0;
		Iterable<Entity> nchild = listEntities(childKindn, nfkName,
				String.valueOf(entity.getKey().getName()));
		
		
		for (Entity nen : nchild) {
			
			
	        
	        
			sbwNJ.append("{");
			for (String nchildkey : nen.getProperties().keySet()) {
				if (nchildkey.equals("ncreatedOn") || nchildkey.equals("nmodifiedOn") || nchildkey.equals("nsyncDate")) {
					try {
						String ndateSDString = nen.getProperties().get(nchildkey).toString();
					    long mensyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(ndateSDString).getTime();
					    
					    sbwNJ.append("\"" + nchildkey + "\" : " + mensyncepoch + ",");
					    }
				        catch(ParseException pe) {
				            System.out.println("ERROR: EPOCH conversion for ME-N date conversion --- could not parse date in string");
				        }
				}
				else if (nchildkey.equals("ndeleted")) {
					sbwNJ.append("\"" + nchildkey + "\" : " + nen.getProperties().get(nchildkey) + ",");
				}
				else {
					sbwNJ.append("\"" + nchildkey + "\" : \"" + nen.getProperties().get(nchildkey) + "\",");	
				}	
				
		        
		        
		        
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

	
	public static String writeAgAcNotesJSON(Entity entity, String childKindn,
			String nfkName) {
		//logger.log(Level.INFO, "creating Ag/AcNotes JSON format---$---");
		StringBuilder wAgAcNJSON = new StringBuilder();
		StringBuilder wAgAcNJHeading1 = new StringBuilder();
		StringBuilder wAgAcNJHeading2 = new StringBuilder();
		StringBuilder wAgAcNJreport = new StringBuilder();
		int wAgIDForN = 0;
		int wAgIDForN1 = 0;
		int AgIDForNotesensum = 0;
		StringBuilder AgNotesList = new StringBuilder();
		String childequals = "";
		int j = 0;

		StringBuilder sbwAgAcNJ = new StringBuilder();
		StringBuilder sbwAgAcNJNested = new StringBuilder();
		StringBuilder sbwAgAcNJ2 = new StringBuilder();
		StringBuilder agnsbdl = new StringBuilder();
		int wAgAcNJ = 0;
		Iterable<Entity> nchild = listEntities(childKindn, nfkName,
				String.valueOf(entity.getKey().getName()));
		
		
		
		if (childKindn.equals("Agendaitem")) {
			sbwAgAcNJ = sbwAgAcNJ.append("\"magendaItemsObjects\":[");
			sbwAgAcNJ2 = sbwAgAcNJ2.append("\"aginotesObjects\":[");
			childequals = "agendaItemID";
		} else if (childKindn.equals("Actionitem")) {
			sbwAgAcNJ = sbwAgAcNJ.append(",\"mactionItemsObjects\":[");
			sbwAgAcNJ2 = sbwAgAcNJ2.append("\"acinotesObjects\":[");
			childequals = "actionItemID";
		}

		for (Entity nen : nchild) {
			
		
			sbwAgAcNJ.append("{");
			
			for (String nchildkey : nen.getProperties().keySet()) {
				
				if (childKindn.equals("Agendaitem")) {
					
					if (nchildkey.equals("agiactualEnd") || nchildkey.equals("agiactualStart") || nchildkey.equals("agicreatedOn") || nchildkey.equals("agimodifiedOn") || nchildkey.equals("agisyncDate")) {
						try {
							String agdateSDString = nen.getProperties().get(nchildkey).toString();
						    long meagsyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(agdateSDString).getTime();
						    
						    sbwAgAcNJ.append("\"" + nchildkey + "\" : " + meagsyncepoch + ",");
						    }
					        catch(ParseException pe) {
					            System.out.println("ERROR: EPOCH conversion for ME-AG date conversion --- could not parse date in string");
					        }
					}
					else if (nchildkey.equals("agiduration") || nchildkey.equals("agiorder") || nchildkey.equals("agicompleted") || nchildkey.equals("agideleted") || nchildkey.equals("agiorder")) {
						sbwAgAcNJ.append("\"" + nchildkey + "\" : " + nen.getProperties().get(nchildkey) + ",");
					}
					else if (nchildkey.equals("agiactionItems")) {
						sbwAgAcNJ.append("\"agiactionItemsObjects\":[");
							String agnpstr = String.valueOf(nen.getProperties().get(nchildkey));
							if (!agnpstr.isEmpty())
							{
							String[] agnptemp;
							String agnpdelimiter = ";";
							agnptemp = agnpstr.split(agnpdelimiter);
							//-
							for (int agnpsi = 0; agnpsi < agnptemp.length; agnpsi++) {
								
								sbwAgAcNJ.append("{");
								if (agnptemp[agnpsi] != null) {
									Iterable<Entity> pschild = listEntities("Actionitem",
											"actionItemID", agnptemp[agnpsi]);

									for (Entity agnpsen : pschild) {
										for (String agnpskey : agnpsen.getProperties().keySet()) {
											if (agnpskey.equals("acicreatedOn") || agnpskey.equals("acidueDate") || agnpskey.equals("acimodifiedOn")|| agnpskey.equals("acisyncDate")) {
												try {
													String agnacdateSDString = agnpsen.getProperties().get(agnpskey).toString();
												    long agnmeacsyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(agnacdateSDString).getTime();
												    
												    sbwAgAcNJ.append("\"" + agnpskey + "\" : " + agnmeacsyncepoch + ",");
												    }
											        catch(ParseException agnpe) {
											            System.out.println("ERROR: EPOCH conversion for ME-AG date conversion --- could not parse date in string");
											        }
											}
											else if (agnpskey.equals("acideleted") || agnpskey.equals("aciorder")) {
												sbwAgAcNJ.append("\"" + agnpskey + "\" : " + agnpsen.getProperties().get(agnpskey) + ",");
											}
											else {
												sbwAgAcNJ.append("\"" + agnpskey + "\" : \"" + agnpsen.getProperties().get(agnpskey) + "\",");	
											}
										}
										j++;
										if (j > 0) {
											sbwAgAcNJ.deleteCharAt(sbwAgAcNJ.lastIndexOf(","));
										}
									}
								}
								sbwAgAcNJ.append("},");
							}
							;
							sbwAgAcNJ.deleteCharAt(sbwAgAcNJ.lastIndexOf(","));
							}
							sbwAgAcNJ.append("],");
					}
					else {
						sbwAgAcNJ.append("\"" + nchildkey + "\" : \"" + nen.getProperties().get(nchildkey) + "\",");	
					}
					
					
							
					
					
				} else if (childKindn.equals("Actionitem")) {
					
					if (nchildkey.equals("acicreatedOn") || nchildkey.equals("acidueDate") || nchildkey.equals("acimodifiedOn")|| nchildkey.equals("acisyncDate")) {
						try {
							String acdateSDString = nen.getProperties().get(nchildkey).toString();
						    long meacsyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(acdateSDString).getTime();
						    
						    sbwAgAcNJ.append("\"" + nchildkey + "\" : " + meacsyncepoch + ",");
						    }
					        catch(ParseException pe) {
					            System.out.println("ERROR: EPOCH conversion for ME-AG date conversion --- could not parse date in string");
					        }
					}
					else if (nchildkey.equals("acideleted") || nchildkey.equals("aciorder")) {
						sbwAgAcNJ.append("\"" + nchildkey + "\" : " + nen.getProperties().get(nchildkey) + ",");
					}
					else {
						sbwAgAcNJ.append("\"" + nchildkey + "\" : \"" + nen.getProperties().get(nchildkey) + "\",");	
					}
					
					
							
				}
				
				
				
				
				
				
				if (nchildkey.equals(childequals)) {
					
					
					
					
					String AgIDForNotes = String.valueOf(nen.getProperties()
							.get(nchildkey));
					
					sbwAgAcNJNested = sbwAgAcNJNested.append(sbwAgAcNJ2);

				
					if (AgIDForNotes != null) {
						Iterable<Entity> AgForNoteschild = listEntities("Note",
								"nparentID", AgIDForNotes);
						

						for (Entity AgIDForNotesenSumE : AgForNoteschild) {
							AgIDForNotesensum++;
				        }
					
						for (Entity AgIDForNotesen : AgForNoteschild) {
													
							
							sbwAgAcNJNested.append("{");
							for (String AgForNoteskey : AgIDForNotesen
									.getProperties().keySet()) {
								
							
						if (AgForNoteskey.equals("ncreatedOn") || AgForNoteskey.equals("nmodifiedOn") || AgForNoteskey.equals("nsyncDate")) {
							try {
								String ndateSDString = AgIDForNotesen.getProperties().get(AgForNoteskey).toString();
							    long mensyncepoch = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(ndateSDString).getTime();
							    
							    sbwAgAcNJNested.append("\"" + AgForNoteskey + "\" : " + mensyncepoch + ",");
							    }
						        catch(ParseException pe) {
						            System.out.println("ERROR: EPOCH conversion for ME-N date conversion --- could not parse date in string");
						        }
						}
						else if (AgForNoteskey.equals("ndeleted")) {
							sbwAgAcNJNested.append("\"" + AgForNoteskey + "\" : " + AgIDForNotesen.getProperties().get(AgForNoteskey) + ",");
						}
						else {
							sbwAgAcNJNested.append("\"" + AgForNoteskey + "\" : \"" + AgIDForNotesen.getProperties().get(AgForNoteskey) + "\",");	
						}
								
								
							
								
									
										
											
								wAgIDForN++;
								
							}
							if (wAgIDForN > 1) {
								sbwAgAcNJNested.deleteCharAt(sbwAgAcNJNested
										.lastIndexOf(","));
							}
							
							sbwAgAcNJNested.append("}");
							if (AgIDForNotesensum > 1) { sbwAgAcNJNested.append(","); }

						}
						wAgIDForN1++;

					}
					if (AgIDForNotesensum > 1) { sbwAgAcNJNested.deleteCharAt(sbwAgAcNJNested.lastIndexOf(",")); }
					sbwAgAcNJNested.append("]},");
					
					

				};
				

				
				wAgIDForN1++;
			}
			wAgAcNJ++;
			
				
			
			
			
				
			
			
			
			if (sbwAgAcNJNested != null) {
				sbwAgAcNJ = sbwAgAcNJ.append(sbwAgAcNJNested);
				sbwAgAcNJNested.setLength(0);

			}

			
			

			wAgAcNJHeading1 = wAgAcNJHeading1.append("]},");
			wAgAcNJHeading2 = wAgAcNJHeading2.append("]},");

			/**
			 * if (wAgIDForN > 0)
			 * 
			 * { AgNotesList.deleteCharAt(AgNotesList.lastIndexOf(",")); }
			 */
			
		}

		if (wAgAcNJ > 0) {
			sbwAgAcNJ.deleteCharAt(sbwAgAcNJ.lastIndexOf(","));
		}
		
		
		sbwAgAcNJ = sbwAgAcNJ.append("]");
		
		wAgAcNJSON = wAgAcNJSON.append(wAgAcNJHeading1);
		wAgAcNJSON = wAgAcNJSON.append(wAgAcNJHeading2);

		return sbwAgAcNJ.toString();

	}


		
	
	public static String writeAcNotesJSON(Entity entity, String childKindn,
			String nfkName) {
		//logger.log(Level.INFO, "creating Notes JSON format---*---");
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
		//logger.log(Level.INFO, "Adding entity to cache");
		keycache.put(key, entity);
	}

	/**
	 * Delete the entity from cache
	 * 
	 * @param key
	 *            : Key of the entity that needs to be deleted
	 */
	public static void deleteFromCache(Key key) {
		//logger.log(Level.INFO, "Deleting entity from cache");
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
		//logger.log(Level.INFO, "Searching entity in cache");
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