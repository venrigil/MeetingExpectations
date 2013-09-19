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
import javax.persistence.Id;
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
@RooJpaActiveRecord(identifierType = String.class, versionType = Long.class)
@RooJson
public class Note {

    @NotNull
	private String ntext;
	
	private String ncreatedBy;

    @PrimaryKey
    @Id
    private String PrimaryId;
    
    private String noteID;
	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date ncreatedOn = toNearestWholeHourN();
/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date createdtime = toNearestWholeHourN();
*/
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date nmodifiedOn = toNearestWholeHourN();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date nsyncDate = toNearestWholeHourN();
/*    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "-M")
    private java.util.Date modifiedtime = toNearestWholeHourN();
*/
	private String ntitle;
	
	private String nparentType;
	private String nparentID;
	
	private String nmaaID;
	
    private Boolean ndeleted;

   	private String nmeetings;
	private String nagendaItems;
	private String nparticipants;
	private String nnotes;
	private String nactionItems;
	private String nWebCreated;
	private String niPadAppCreated;
	private String nAndroidCreated;
	private String nOtherCreated;
	private String nAddlColumn1;
	private String nAddlColumn2;
	private String nAddlColumn3;
	private String nAddlColumn4;
	private String nAddlColumn5;

    static Date toNearestWholeHourN() {
   	 
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        c.setTime(d);

        if (c.get(Calendar.MINUTE) >= 1)
            c.add(Calendar.HOUR, 1);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
        }


	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Note().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countNotes() {
        return findAllNotes().size();
    }

	@Transactional
    public static List<Note> findAllNotes() {
        return entityManager().createQuery("SELECT o FROM Note o", Note.class).getResultList();
    }

	@Transactional
    public static Note findNote(String PrimaryId) {
        if (PrimaryId == null || PrimaryId.length() == 0) return null;
        return entityManager().find(Note.class, PrimaryId);
    }

	@Transactional
    public static List<Note> findNoteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Note o", Note.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Note attached = Note.findNote(this.PrimaryId);
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
    public Note merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Note merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Note fromJsonToNote(String json) {
        return new JSONDeserializer<Note>().use(null, Note.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Note> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Note> fromJsonArrayToNotes(String json) {
        return new JSONDeserializer<List<Note>>().use(null, ArrayList.class).use("values", Note.class).deserialize(json);
    }

	public String getNtext() {
        return this.ntext;
    }

	public void setNtext(String ntext) {
        this.ntext = ntext;
    }

	public String getNcreatedBy() {
        return this.ncreatedBy;
    }

	public void setNcreatedBy(String ncreatedBy) {
        this.ncreatedBy = ncreatedBy;
    }

	public String getPrimaryId() {
        return this.PrimaryId;
    }

	public void setPrimaryId(String PrimaryId) {
        this.PrimaryId = PrimaryId;
    }

	public String getNoteID() {
        return this.noteID;
    }

	public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

	public Date getNcreatedOn() {
        return this.ncreatedOn;
    }

	public void setNcreatedOn(Date ncreatedOn) {
        this.ncreatedOn = ncreatedOn;
    }

	public Date getNmodifiedOn() {
        return this.nmodifiedOn;
    }

	public void setNmodifiedOn(Date nmodifiedOn) {
        this.nmodifiedOn = nmodifiedOn;
    }

	public Date getNsyncDate() {
        return this.nsyncDate;
    }

	public void setNsyncDate(Date nsyncDate) {
        this.nsyncDate = nsyncDate;
    }

	public String getNtitle() {
        return this.ntitle;
    }

	public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

	public String getNparentType() {
        return this.nparentType;
    }

	public void setNparentType(String nparentType) {
        this.nparentType = nparentType;
    }

	public String getNparentID() {
        return this.nparentID;
    }

	public void setNparentID(String nparentID) {
        this.nparentID = nparentID;
    }

	public String getNmaaID() {
        return this.nmaaID;
    }

	public void setNmaaID(String nmaaID) {
        this.nmaaID = nmaaID;
    }

	public Boolean getNdeleted() {
        return this.ndeleted;
    }

	public void setNdeleted(Boolean ndeleted) {
        this.ndeleted = ndeleted;
    }

	public String getNmeetings() {
        return this.nmeetings;
    }

	public void setNmeetings(String nmeetings) {
        this.nmeetings = nmeetings;
    }

	public String getNagendaItems() {
        return this.nagendaItems;
    }

	public void setNagendaItems(String nagendaItems) {
        this.nagendaItems = nagendaItems;
    }

	public String getNparticipants() {
        return this.nparticipants;
    }

	public void setNparticipants(String nparticipants) {
        this.nparticipants = nparticipants;
    }

	public String getNnotes() {
        return this.nnotes;
    }

	public void setNnotes(String nnotes) {
        this.nnotes = nnotes;
    }

	public String getNactionItems() {
        return this.nactionItems;
    }

	public void setNactionItems(String nactionItems) {
        this.nactionItems = nactionItems;
    }

	public String getNWebCreated() {
        return this.nWebCreated;
    }

	public void setNWebCreated(String nWebCreated) {
        this.nWebCreated = nWebCreated;
    }

	public String getNiPadAppCreated() {
        return this.niPadAppCreated;
    }

	public void setNiPadAppCreated(String niPadAppCreated) {
        this.niPadAppCreated = niPadAppCreated;
    }

	public String getNAndroidCreated() {
        return this.nAndroidCreated;
    }

	public void setNAndroidCreated(String nAndroidCreated) {
        this.nAndroidCreated = nAndroidCreated;
    }

	public String getNOtherCreated() {
        return this.nOtherCreated;
    }

	public void setNOtherCreated(String nOtherCreated) {
        this.nOtherCreated = nOtherCreated;
    }

	public String getNAddlColumn1() {
        return this.nAddlColumn1;
    }

	public void setNAddlColumn1(String nAddlColumn1) {
        this.nAddlColumn1 = nAddlColumn1;
    }

	public String getNAddlColumn2() {
        return this.nAddlColumn2;
    }

	public void setNAddlColumn2(String nAddlColumn2) {
        this.nAddlColumn2 = nAddlColumn2;
    }

	public String getNAddlColumn3() {
        return this.nAddlColumn3;
    }

	public void setNAddlColumn3(String nAddlColumn3) {
        this.nAddlColumn3 = nAddlColumn3;
    }

	public String getNAddlColumn4() {
        return this.nAddlColumn4;
    }

	public void setNAddlColumn4(String nAddlColumn4) {
        this.nAddlColumn4 = nAddlColumn4;
    }

	public String getNAddlColumn5() {
        return this.nAddlColumn5;
    }

	public void setNAddlColumn5(String nAddlColumn5) {
        this.nAddlColumn5 = nAddlColumn5;
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
