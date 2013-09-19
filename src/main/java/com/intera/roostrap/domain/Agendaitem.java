package com.intera.roostrap.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;
import java.util.Date;
import java.util.Locale;
import java.util.GregorianCalendar;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.persistence.Id;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class)
public class Agendaitem {

    private String agititle;
	
	private Integer agiduration;
	
	private String agitype;
	
	private String agipresenter;
	
	private Integer agiorder;

    @PrimaryKey
    @Id
    private String PrimaryId;
    
    private String agendaItemID;
	
	private String agiid;
    
    private String agimeetingID;
    
    @Size(max = 4000)
    private String agidesc;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date agiactualStart = toNearestWholeHourAg();
/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date actualStarttime = toNearestWholeHourAg();
*/
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date agiactualEnd = toNearestWholeHourAg();
/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date actualEndtime = toNearestWholeHourAg();
*/
    private Boolean agicompleted;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date agicreatedOn = toNearestWholeHourAg();
/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date createdtime = toNearestWholeHourAg();
*/  
    private Boolean agideleted;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date agimodifiedOn = toNearestWholeHourAg();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date agisyncDate = toNearestWholeHourAg();

/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date modifiedtime = toNearestWholeHourAg();
*/

	private String agimeetings;
	private String agiagendaItems;
	private String agiparticipants;
	private String aginotes;
	private String agiactionItems;
	private String agiWebCreated;
	private String agiiPadAppCreated;
	private String agiAndroidCreated;
	private String agiOtherCreated;
	private String agiAddlColumn1;
	private String agiAddlColumn2;
	private String agiAddlColumn3;
	private String agiAddlColumn4;
	private String agiAddlColumn5;
	
    private String agicreatedBy;
    
	
    
    
    static Date toNearestWholeHourAg() {
   	 
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        c.setTime(d);

        if (c.get(Calendar.MINUTE) >= 1)
            c.add(Calendar.HOUR, 1);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
        }

	public String getAgititle() {
        return this.agititle;
    }

	public void setAgititle(String agititle) {
        this.agititle = agititle;
    }

	public Integer getAgiduration() {
        return this.agiduration;
    }

	public void setAgiduration(Integer agiduration) {
        this.agiduration = agiduration;
    }

	public String getAgitype() {
        return this.agitype;
    }

	public void setAgitype(String agitype) {
        this.agitype = agitype;
    }

	public String getAgipresenter() {
        return this.agipresenter;
    }

	public void setAgipresenter(String agipresenter) {
        this.agipresenter = agipresenter;
    }

	public Integer getAgiorder() {
        return this.agiorder;
    }

	public void setAgiorder(Integer agiorder) {
        this.agiorder = agiorder;
    }

	public String getPrimaryId() {
        return this.PrimaryId;
    }

	public void setPrimaryId(String PrimaryId) {
        this.PrimaryId = PrimaryId;
    }

	public String getAgendaItemID() {
        return this.agendaItemID;
    }

	public void setAgendaItemID(String agendaItemID) {
        this.agendaItemID = agendaItemID;
    }

	public String getAgiid() {
        return this.agiid;
    }

	public void setAgiid(String agiid) {
        this.agiid = agiid;
    }

	public String getAgimeetingID() {
        return this.agimeetingID;
    }

	public void setAgimeetingID(String agimeetingID) {
        this.agimeetingID = agimeetingID;
    }

	public String getAgidesc() {
        return this.agidesc;
    }

	public void setAgidesc(String agidesc) {
        this.agidesc = agidesc;
    }

	public Date getAgiactualStart() {
        return this.agiactualStart;
    }

	public void setAgiactualStart(Date agiactualStart) {
        this.agiactualStart = agiactualStart;
    }

	public Date getAgiactualEnd() {
        return this.agiactualEnd;
    }

	public void setAgiactualEnd(Date agiactualEnd) {
        this.agiactualEnd = agiactualEnd;
    }

	public Boolean getAgicompleted() {
        return this.agicompleted;
    }

	public void setAgicompleted(Boolean agicompleted) {
        this.agicompleted = agicompleted;
    }

	public Date getAgicreatedOn() {
        return this.agicreatedOn;
    }

	public void setAgicreatedOn(Date agicreatedOn) {
        this.agicreatedOn = agicreatedOn;
    }

	public Boolean getAgideleted() {
        return this.agideleted;
    }

	public void setAgideleted(Boolean agideleted) {
        this.agideleted = agideleted;
    }

	public Date getAgimodifiedOn() {
        return this.agimodifiedOn;
    }

	public void setAgimodifiedOn(Date agimodifiedOn) {
        this.agimodifiedOn = agimodifiedOn;
    }

	public Date getAgisyncDate() {
        return this.agisyncDate;
    }

	public void setAgisyncDate(Date agisyncDate) {
        this.agisyncDate = agisyncDate;
    }

	public String getAgimeetings() {
        return this.agimeetings;
    }

	public void setAgimeetings(String agimeetings) {
        this.agimeetings = agimeetings;
    }

	public String getAgiagendaItems() {
        return this.agiagendaItems;
    }

	public void setAgiagendaItems(String agiagendaItems) {
        this.agiagendaItems = agiagendaItems;
    }

	public String getAgiparticipants() {
        return this.agiparticipants;
    }

	public void setAgiparticipants(String agiparticipants) {
        this.agiparticipants = agiparticipants;
    }

	public String getAginotes() {
        return this.aginotes;
    }

	public void setAginotes(String aginotes) {
        this.aginotes = aginotes;
    }

	public String getAgiactionItems() {
        return this.agiactionItems;
    }

	public void setAgiactionItems(String agiactionItems) {
        this.agiactionItems = agiactionItems;
    }

	public String getAgiWebCreated() {
        return this.agiWebCreated;
    }

	public void setAgiWebCreated(String agiWebCreated) {
        this.agiWebCreated = agiWebCreated;
    }

	public String getAgiiPadAppCreated() {
        return this.agiiPadAppCreated;
    }

	public void setAgiiPadAppCreated(String agiiPadAppCreated) {
        this.agiiPadAppCreated = agiiPadAppCreated;
    }

	public String getAgiAndroidCreated() {
        return this.agiAndroidCreated;
    }

	public void setAgiAndroidCreated(String agiAndroidCreated) {
        this.agiAndroidCreated = agiAndroidCreated;
    }

	public String getAgiOtherCreated() {
        return this.agiOtherCreated;
    }

	public void setAgiOtherCreated(String agiOtherCreated) {
        this.agiOtherCreated = agiOtherCreated;
    }

	public String getAgiAddlColumn1() {
        return this.agiAddlColumn1;
    }

	public void setAgiAddlColumn1(String agiAddlColumn1) {
        this.agiAddlColumn1 = agiAddlColumn1;
    }

	public String getAgiAddlColumn2() {
        return this.agiAddlColumn2;
    }

	public void setAgiAddlColumn2(String agiAddlColumn2) {
        this.agiAddlColumn2 = agiAddlColumn2;
    }

	public String getAgiAddlColumn3() {
        return this.agiAddlColumn3;
    }

	public void setAgiAddlColumn3(String agiAddlColumn3) {
        this.agiAddlColumn3 = agiAddlColumn3;
    }

	public String getAgiAddlColumn4() {
        return this.agiAddlColumn4;
    }

	public void setAgiAddlColumn4(String agiAddlColumn4) {
        this.agiAddlColumn4 = agiAddlColumn4;
    }

	public String getAgiAddlColumn5() {
        return this.agiAddlColumn5;
    }

	public void setAgiAddlColumn5(String agiAddlColumn5) {
        this.agiAddlColumn5 = agiAddlColumn5;
    }

	public String getAgicreatedBy() {
        return this.agicreatedBy;
    }

	public void setAgicreatedBy(String agicreatedBy) {
        this.agicreatedBy = agicreatedBy;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Agendaitem fromJsonToAgendaitem(String json) {
        return new JSONDeserializer<Agendaitem>().use(null, Agendaitem.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Agendaitem> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Agendaitem> fromJsonArrayToAgendaitems(String json) {
        return new JSONDeserializer<List<Agendaitem>>().use(null, ArrayList.class).use("values", Agendaitem.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Agendaitem().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countAgendaitems() {
        return findAllAgendaitems().size();
    }

	@Transactional
    public static List<Agendaitem> findAllAgendaitems() {
        return entityManager().createQuery("SELECT o FROM Agendaitem o", Agendaitem.class).getResultList();
    }

	@Transactional
    public static Agendaitem findAgendaitem(String PrimaryId) {
        if (PrimaryId == null || PrimaryId.length() == 0) return null;
        return entityManager().find(Agendaitem.class, PrimaryId);
    }

	@Transactional
    public static List<Agendaitem> findAgendaitemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Agendaitem o", Agendaitem.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Agendaitem attached = Agendaitem.findAgendaitem(this.PrimaryId);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Agendaitem merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Agendaitem merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Version
    @Column(name = "version")
    private Long version;

	public Long getVersion() {
        return this.version;
    }

	public void setVersion(Long version) {
        this.version = version;
    }
}

