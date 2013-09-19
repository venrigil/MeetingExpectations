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


@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class)
public class Directory {
	
	private String pfirstName;

    private String plastName;
	
	private String pemail;

    @PrimaryKey
    @Id
    private String PrimaryId;
    
    private String contactID;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date pcreatedOn = toNearestWholeHourD();
    
    
/*
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private Date createdtime = toNearestWholeHourD();
*/
    private Boolean pdeleted;
    

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date pmodifiedOn = toNearestWholeHourD();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date psyncDate = toNearestWholeHourD();
    
    
/*
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private Date modifiedtime = toNearestWholeHourD();
*/

	private String pmeetings;
	private String pagendaItems;
	private String pparticipants;
	private String pnotes;
	private String pactionItems;
	private String pWebCreated;
	private String piPadAppCreated;
	private String pAndroidCreated;
	private String pOtherCreated;
	private String pAddlColumn1;
	private String pAddlColumn2;
	private String pAddlColumn3;
	private String pAddlColumn4;
	private String pAddlColumn5;
	
	private String pcreatedBy;

    static Date toNearestWholeHourD() {
    	 
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        c.setTime(d);
        if (c.get(Calendar.MINUTE) >= 1) c.add(Calendar.HOUR, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();

    }

	public String getPfirstName() {
        return this.pfirstName;
    }

	public void setPfirstName(String pfirstName) {
        this.pfirstName = pfirstName;
    }

	public String getPlastName() {
        return this.plastName;
    }

	public void setPlastName(String plastName) {
        this.plastName = plastName;
    }

	public String getPemail() {
        return this.pemail;
    }

	public void setPemail(String pemail) {
        this.pemail = pemail;
    }

	public String getPrimaryId() {
        return this.PrimaryId;
    }

	public void setPrimaryId(String PrimaryId) {
        this.PrimaryId = PrimaryId;
    }

	public String getContactID() {
        return this.contactID;
    }

	public void setContactID(String contactID) {
        this.contactID = contactID;
    }

	public Date getPcreatedOn() {
        return this.pcreatedOn;
    }

	public void setPcreatedOn(Date pcreatedOn) {
        this.pcreatedOn = pcreatedOn;
    }

	public Boolean getPdeleted() {
        return this.pdeleted;
    }

	public void setPdeleted(Boolean pdeleted) {
        this.pdeleted = pdeleted;
    }

	public Date getPmodifiedOn() {
        return this.pmodifiedOn;
    }

	public void setPmodifiedOn(Date pmodifiedOn) {
        this.pmodifiedOn = pmodifiedOn;
    }

	public Date getPsyncDate() {
        return this.psyncDate;
    }

	public void setPsyncDate(Date psyncDate) {
        this.psyncDate = psyncDate;
    }

	public String getPmeetings() {
        return this.pmeetings;
    }

	public void setPmeetings(String pmeetings) {
        this.pmeetings = pmeetings;
    }

	public String getPagendaItems() {
        return this.pagendaItems;
    }

	public void setPagendaItems(String pagendaItems) {
        this.pagendaItems = pagendaItems;
    }

	public String getPparticipants() {
        return this.pparticipants;
    }

	public void setPparticipants(String pparticipants) {
        this.pparticipants = pparticipants;
    }

	public String getPnotes() {
        return this.pnotes;
    }

	public void setPnotes(String pnotes) {
        this.pnotes = pnotes;
    }

	public String getPactionItems() {
        return this.pactionItems;
    }

	public void setPactionItems(String pactionItems) {
        this.pactionItems = pactionItems;
    }

	public String getPWebCreated() {
        return this.pWebCreated;
    }

	public void setPWebCreated(String pWebCreated) {
        this.pWebCreated = pWebCreated;
    }

	public String getPiPadAppCreated() {
        return this.piPadAppCreated;
    }

	public void setPiPadAppCreated(String piPadAppCreated) {
        this.piPadAppCreated = piPadAppCreated;
    }

	public String getPAndroidCreated() {
        return this.pAndroidCreated;
    }

	public void setPAndroidCreated(String pAndroidCreated) {
        this.pAndroidCreated = pAndroidCreated;
    }

	public String getPOtherCreated() {
        return this.pOtherCreated;
    }

	public void setPOtherCreated(String pOtherCreated) {
        this.pOtherCreated = pOtherCreated;
    }

	public String getPAddlColumn1() {
        return this.pAddlColumn1;
    }

	public void setPAddlColumn1(String pAddlColumn1) {
        this.pAddlColumn1 = pAddlColumn1;
    }

	public String getPAddlColumn2() {
        return this.pAddlColumn2;
    }

	public void setPAddlColumn2(String pAddlColumn2) {
        this.pAddlColumn2 = pAddlColumn2;
    }

	public String getPAddlColumn3() {
        return this.pAddlColumn3;
    }

	public void setPAddlColumn3(String pAddlColumn3) {
        this.pAddlColumn3 = pAddlColumn3;
    }

	public String getPAddlColumn4() {
        return this.pAddlColumn4;
    }

	public void setPAddlColumn4(String pAddlColumn4) {
        this.pAddlColumn4 = pAddlColumn4;
    }

	public String getPAddlColumn5() {
        return this.pAddlColumn5;
    }

	public void setPAddlColumn5(String pAddlColumn5) {
        this.pAddlColumn5 = pAddlColumn5;
    }

	public String getPcreatedBy() {
        return this.pcreatedBy;
    }

	public void setPcreatedBy(String pcreatedBy) {
        this.pcreatedBy = pcreatedBy;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Directory fromJsonToDirectory(String json) {
        return new JSONDeserializer<Directory>().use(null, Directory.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Directory> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Directory> fromJsonArrayToDirectorys(String json) {
        return new JSONDeserializer<List<Directory>>().use(null, ArrayList.class).use("values", Directory.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Directory().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countDirectorys() {
        return findAllDirectorys().size();
    }

	@Transactional
    public static List<Directory> findAllDirectorys() {
        return entityManager().createQuery("SELECT o FROM Directory o", Directory.class).getResultList();
    }

	@Transactional
    public static Directory findDirectory(String PrimaryId) {
        if (PrimaryId == null || PrimaryId.length() == 0) return null;
        return entityManager().find(Directory.class, PrimaryId);
    }

	@Transactional
    public static List<Directory> findDirectoryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Directory o", Directory.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Directory attached = Directory.findDirectory(this.PrimaryId);
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
    public Directory merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Directory merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
