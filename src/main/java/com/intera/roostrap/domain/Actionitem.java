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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@RooJpaActiveRecord
@RooJson
public class Actionitem {

	@Id
	@PrimaryKey
	private String PrimaryId;
	
    private String actionItemID;
	
    private String aciassignee;
	
    @Size(max = 4000)
    private String acidesc;
    
    private Boolean acideleted;

    private String acimeetingID;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date acicreatedOn = toNearestWholeHourAc();
/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date createdtime = toNearestWholeHourAc();
*/
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date acimodifiedOn = toNearestWholeHourAc();
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date acisyncDate = toNearestWholeHourAc();
/*
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date modifiedtime = toNearestWholeHourAc();
*/
    private String acistatus;
	
    private Integer aciorder;
	
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date acidueDate = toNearestWholeHourAc();

	private String acimeetings;
	private String aciagendaItems;
	private String aciparticipants;
	private String acinotes;
	private String aciactionItems;
	private String aciWebCreated;
	private String aciiPadAppCreated;
	private String aciAndroidCreated;
	private String aciOtherCreated;
	private String aciAddlColumn1;
	private String aciAddlColumn2;
	private String aciAddlColumn3;
	private String aciAddlColumn4;
	private String aciAddlColumn5;
	
    private String acicreatedBy;
    
	private String aciparentType;
	private String aciparentID;
	
    static Date toNearestWholeHourAc() {
   	 
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        c.setTime(d);

        if (c.get(Calendar.MINUTE) >= 1)
            c.add(Calendar.HOUR, 1);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
        }


	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Actionitem fromJsonToActionitem(String json) {
        return new JSONDeserializer<Actionitem>().use(null, Actionitem.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Actionitem> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Actionitem> fromJsonArrayToActionitems(String json) {
        return new JSONDeserializer<List<Actionitem>>().use(null, ArrayList.class).use("values", Actionitem.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Actionitem().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countActionitems() {
        return findAllActionitems().size();
    }

	@Transactional
    public static List<Actionitem> findAllActionitems() {
        return entityManager().createQuery("SELECT o FROM Actionitem o", Actionitem.class).getResultList();
    }

	@Transactional
    public static Actionitem findActionitem(String PrimaryId) {
        if (PrimaryId == null || PrimaryId.length() == 0) return null;
        return entityManager().find(Actionitem.class, PrimaryId);
    }

	@Transactional
    public static List<Actionitem> findActionitemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Actionitem o", Actionitem.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Actionitem attached = Actionitem.findActionitem(this.PrimaryId);
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
    public Actionitem merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Actionitem merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getPrimaryId() {
        return this.PrimaryId;
    }

	public void setPrimaryId(String PrimaryId) {
        this.PrimaryId = PrimaryId;
    }

	public String getActionItemID() {
        return this.actionItemID;
    }

	public void setActionItemID(String actionItemID) {
        this.actionItemID = actionItemID;
    }

	public String getAciassignee() {
        return this.aciassignee;
    }

	public void setAciassignee(String aciassignee) {
        this.aciassignee = aciassignee;
    }

	public String getAcidesc() {
        return this.acidesc;
    }

	public void setAcidesc(String acidesc) {
        this.acidesc = acidesc;
    }

	public Boolean getAcideleted() {
        return this.acideleted;
    }

	public void setAcideleted(Boolean acideleted) {
        this.acideleted = acideleted;
    }

	public String getAcimeetingID() {
        return this.acimeetingID;
    }

	public void setAcimeetingID(String acimeetingID) {
        this.acimeetingID = acimeetingID;
    }

	public Date getAcicreatedOn() {
        return this.acicreatedOn;
    }

	public void setAcicreatedOn(Date acicreatedOn) {
        this.acicreatedOn = acicreatedOn;
    }

	public Date getAcimodifiedOn() {
        return this.acimodifiedOn;
    }

	public void setAcimodifiedOn(Date acimodifiedOn) {
        this.acimodifiedOn = acimodifiedOn;
    }

	public Date getAcisyncDate() {
        return this.acisyncDate;
    }

	public void setAcisyncDate(Date acisyncDate) {
        this.acisyncDate = acisyncDate;
    }

	public String getAcistatus() {
        return this.acistatus;
    }

	public void setAcistatus(String acistatus) {
        this.acistatus = acistatus;
    }

	public Integer getAciorder() {
        return this.aciorder;
    }

	public void setAciorder(Integer aciorder) {
        this.aciorder = aciorder;
    }

	public Date getAcidueDate() {
        return this.acidueDate;
    }

	public void setAcidueDate(Date acidueDate) {
        this.acidueDate = acidueDate;
    }

	public String getAcimeetings() {
        return this.acimeetings;
    }

	public void setAcimeetings(String acimeetings) {
        this.acimeetings = acimeetings;
    }

	public String getAciagendaItems() {
        return this.aciagendaItems;
    }

	public void setAciagendaItems(String aciagendaItems) {
        this.aciagendaItems = aciagendaItems;
    }

	public String getAciparticipants() {
        return this.aciparticipants;
    }

	public void setAciparticipants(String aciparticipants) {
        this.aciparticipants = aciparticipants;
    }

	public String getAcinotes() {
        return this.acinotes;
    }

	public void setAcinotes(String acinotes) {
        this.acinotes = acinotes;
    }

	public String getAciactionItems() {
        return this.aciactionItems;
    }

	public void setAciactionItems(String aciactionItems) {
        this.aciactionItems = aciactionItems;
    }

	public String getAciWebCreated() {
        return this.aciWebCreated;
    }

	public void setAciWebCreated(String aciWebCreated) {
        this.aciWebCreated = aciWebCreated;
    }

	public String getAciiPadAppCreated() {
        return this.aciiPadAppCreated;
    }

	public void setAciiPadAppCreated(String aciiPadAppCreated) {
        this.aciiPadAppCreated = aciiPadAppCreated;
    }

	public String getAciAndroidCreated() {
        return this.aciAndroidCreated;
    }

	public void setAciAndroidCreated(String aciAndroidCreated) {
        this.aciAndroidCreated = aciAndroidCreated;
    }

	public String getAciOtherCreated() {
        return this.aciOtherCreated;
    }

	public void setAciOtherCreated(String aciOtherCreated) {
        this.aciOtherCreated = aciOtherCreated;
    }

	public String getAciAddlColumn1() {
        return this.aciAddlColumn1;
    }

	public void setAciAddlColumn1(String aciAddlColumn1) {
        this.aciAddlColumn1 = aciAddlColumn1;
    }

	public String getAciAddlColumn2() {
        return this.aciAddlColumn2;
    }

	public void setAciAddlColumn2(String aciAddlColumn2) {
        this.aciAddlColumn2 = aciAddlColumn2;
    }

	public String getAciAddlColumn3() {
        return this.aciAddlColumn3;
    }

	public void setAciAddlColumn3(String aciAddlColumn3) {
        this.aciAddlColumn3 = aciAddlColumn3;
    }

	public String getAciAddlColumn4() {
        return this.aciAddlColumn4;
    }

	public void setAciAddlColumn4(String aciAddlColumn4) {
        this.aciAddlColumn4 = aciAddlColumn4;
    }

	public String getAciAddlColumn5() {
        return this.aciAddlColumn5;
    }

	public void setAciAddlColumn5(String aciAddlColumn5) {
        this.aciAddlColumn5 = aciAddlColumn5;
    }

	public String getAcicreatedBy() {
        return this.acicreatedBy;
    }

	public void setAcicreatedBy(String acicreatedBy) {
        this.acicreatedBy = acicreatedBy;
    }

	public String getAciparentType() {
        return this.aciparentType;
    }

	public void setAciparentType(String aciparentType) {
        this.aciparentType = aciparentType;
    }

	public String getAciparentID() {
        return this.aciparentID;
    }

	public void setAciparentID(String aciparentID) {
        this.aciparentID = aciparentID;
    }

	@Version
    @Column(name = "version")
    private Integer version;

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
}
