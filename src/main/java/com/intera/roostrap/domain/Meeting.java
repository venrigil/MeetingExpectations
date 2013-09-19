package com.intera.roostrap.domain;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;

import org.hibernate.validator.constraints.NotEmpty;
import com.google.appengine.api.datastore.Key;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findMeetingsByMeetingIDEquals", "findMeetingsByMcreatedByEquals" }, identifierType = String.class, versionType = Long.class)
public class Meeting {

	
    
	
	
    private String mtitle;

    @Size(max = 4000)
    private String mdesc;

    private String mlocation;

    @PrimaryKey
    @Id
    private String PrimaryId;
    
    private String meetingID;

    private String mid;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date mdate = toNearestWholeHourM();

    private Integer mplannedDuration;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date mplannedStartDateTime = toNearestWholeHourM();

    private Integer mplannedStart;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date mplannedEndDateTime = toNearestWholeHourEndM();

    private Integer mplannedEnd;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date msyncDate = toNearestWholeHourEndM();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date mcreatedOn = mCreatedTimeM();

    private Integer mactualDuration;

    private String mcreatedBy;

    private Boolean mdeleted;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date mmodifiedOn = mCreatedTimeM();

    @Size(max = 20480)
    private String magendaitemsString;

    @Size(max = 20480)
    private String mparticipantsString;

    @Size(max = 1000)
    private String murl;

    @Size(max = 20480)
    private String mmeetings;

    @Size(max = 20480)
    private String magendaItems;

    @Size(max = 20480)
    private String mparticipants;

    @Size(max = 20480)
    private String mnotes;

    @Size(max = 20480)
    private String mactionItems;

    private String mWebCreated;

    private String miPadAppCreated;

    private String mAndroidCreated;

    private String mOtherCreated;

    private String mAddlColumn1;

    private String mAddlColumn2;

    private String mAddlColumn3;

    private String mAddlColumn4;

    private String mAddlColumn5;

    static Date toNearestWholeHourM() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        c.setTime(d);
        if (c.get(Calendar.MINUTE) >= 1) c.add(Calendar.HOUR, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    static Date toNearestWholeHourEndM() {
        Calendar cend = Calendar.getInstance();
        Date dend = cend.getTime();
        cend.setTime(dend);
        if (cend.get(Calendar.MINUTE) >= 1) cend.add(Calendar.HOUR, 2);
        cend.set(Calendar.MINUTE, 0);
        cend.set(Calendar.SECOND, 0);
        return cend.getTime();
    }

    static Date mCreatedTimeM() {
        Calendar cmct = Calendar.getInstance();
        Date dmct = cmct.getTime();
        cmct.setTime(dmct);
        return cmct.getTime();
    }

    public String toString() {
        return this.meetingID;
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

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Meeting fromJsonToMeeting(String json) {
        return new JSONDeserializer<Meeting>().use(null, Meeting.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Meeting> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Meeting> fromJsonArrayToMeetings(String json) {
        return new JSONDeserializer<List<Meeting>>().use(null, ArrayList.class).use("values", Meeting.class).deserialize(json);
    }

	public String getMtitle() {
        return this.mtitle;
    }

	public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

	public String getMdesc() {
        return this.mdesc;
    }

	public void setMdesc(String mdesc) {
        this.mdesc = mdesc;
    }

	public String getMlocation() {
        return this.mlocation;
    }

	public void setMlocation(String mlocation) {
        this.mlocation = mlocation;
    }

	public String getPrimaryId() {
        return this.PrimaryId;
    }

	public void setPrimaryId(String PrimaryId) {
        this.PrimaryId = PrimaryId;
    }

	public String getMeetingID() {
        return this.meetingID;
    }

	public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

	public String getMid() {
        return this.mid;
    }

	public void setMid(String mid) {
        this.mid = mid;
    }

	public Date getMdate() {
        return this.mdate;
    }

	public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

	public Integer getMplannedDuration() {
        return this.mplannedDuration;
    }

	public void setMplannedDuration(Integer mplannedDuration) {
        this.mplannedDuration = mplannedDuration;
    }

	public Date getMplannedStartDateTime() {
        return this.mplannedStartDateTime;
    }

	public void setMplannedStartDateTime(Date mplannedStartDateTime) {
        this.mplannedStartDateTime = mplannedStartDateTime;
    }

	public Integer getMplannedStart() {
        return this.mplannedStart;
    }

	public void setMplannedStart(Integer mplannedStart) {
        this.mplannedStart = mplannedStart;
    }

	public Date getMplannedEndDateTime() {
        return this.mplannedEndDateTime;
    }

	public void setMplannedEndDateTime(Date mplannedEndDateTime) {
        this.mplannedEndDateTime = mplannedEndDateTime;
    }

	public Integer getMplannedEnd() {
        return this.mplannedEnd;
    }

	public void setMplannedEnd(Integer mplannedEnd) {
        this.mplannedEnd = mplannedEnd;
    }

	public Date getMsyncDate() {
        return this.msyncDate;
    }

	public void setMsyncDate(Date msyncDate) {
        this.msyncDate = msyncDate;
    }

	public Date getMcreatedOn() {
        return this.mcreatedOn;
    }

	public void setMcreatedOn(Date mcreatedOn) {
        this.mcreatedOn = mcreatedOn;
    }

	public Integer getMactualDuration() {
        return this.mactualDuration;
    }

	public void setMactualDuration(Integer mactualDuration) {
        this.mactualDuration = mactualDuration;
    }

	public String getMcreatedBy() {
        return this.mcreatedBy;
    }

	public void setMcreatedBy(String mcreatedBy) {
        this.mcreatedBy = mcreatedBy;
    }

	public Boolean getMdeleted() {
        return this.mdeleted;
    }

	public void setMdeleted(Boolean mdeleted) {
        this.mdeleted = mdeleted;
    }

	public Date getMmodifiedOn() {
        return this.mmodifiedOn;
    }

	public void setMmodifiedOn(Date mmodifiedOn) {
        this.mmodifiedOn = mmodifiedOn;
    }

	public String getMagendaitemsString() {
        return this.magendaitemsString;
    }

	public void setMagendaitemsString(String magendaitemsString) {
        this.magendaitemsString = magendaitemsString;
    }

	public String getMparticipantsString() {
        return this.mparticipantsString;
    }

	public void setMparticipantsString(String mparticipantsString) {
        this.mparticipantsString = mparticipantsString;
    }

	public String getMurl() {
        return this.murl;
    }

	public void setMurl(String murl) {
        this.murl = murl;
    }

	public String getMmeetings() {
        return this.mmeetings;
    }

	public void setMmeetings(String mmeetings) {
        this.mmeetings = mmeetings;
    }

	public String getMagendaItems() {
        return this.magendaItems;
    }

	public void setMagendaItems(String magendaItems) {
        this.magendaItems = magendaItems;
    }

	public String getMparticipants() {
        return this.mparticipants;
    }

	public void setMparticipants(String mparticipants) {
        this.mparticipants = mparticipants;
    }

	public String getMnotes() {
        return this.mnotes;
    }

	public void setMnotes(String mnotes) {
        this.mnotes = mnotes;
    }

	public String getMactionItems() {
        return this.mactionItems;
    }

	public void setMactionItems(String mactionItems) {
        this.mactionItems = mactionItems;
    }

	public String getMWebCreated() {
        return this.mWebCreated;
    }

	public void setMWebCreated(String mWebCreated) {
        this.mWebCreated = mWebCreated;
    }

	public String getMiPadAppCreated() {
        return this.miPadAppCreated;
    }

	public void setMiPadAppCreated(String miPadAppCreated) {
        this.miPadAppCreated = miPadAppCreated;
    }

	public String getMAndroidCreated() {
        return this.mAndroidCreated;
    }

	public void setMAndroidCreated(String mAndroidCreated) {
        this.mAndroidCreated = mAndroidCreated;
    }

	public String getMOtherCreated() {
        return this.mOtherCreated;
    }

	public void setMOtherCreated(String mOtherCreated) {
        this.mOtherCreated = mOtherCreated;
    }

	public String getMAddlColumn1() {
        return this.mAddlColumn1;
    }

	public void setMAddlColumn1(String mAddlColumn1) {
        this.mAddlColumn1 = mAddlColumn1;
    }

	public String getMAddlColumn2() {
        return this.mAddlColumn2;
    }

	public void setMAddlColumn2(String mAddlColumn2) {
        this.mAddlColumn2 = mAddlColumn2;
    }

	public String getMAddlColumn3() {
        return this.mAddlColumn3;
    }

	public void setMAddlColumn3(String mAddlColumn3) {
        this.mAddlColumn3 = mAddlColumn3;
    }

	public String getMAddlColumn4() {
        return this.mAddlColumn4;
    }

	public void setMAddlColumn4(String mAddlColumn4) {
        this.mAddlColumn4 = mAddlColumn4;
    }

	public String getMAddlColumn5() {
        return this.mAddlColumn5;
    }

	public void setMAddlColumn5(String mAddlColumn5) {
        this.mAddlColumn5 = mAddlColumn5;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Meeting().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countMeetings() {
        return findAllMeetings().size();
    }

	@Transactional
    public static List<Meeting> findAllMeetings() {
        return entityManager().createQuery("SELECT o FROM Meeting o", Meeting.class).getResultList();
    }

	@Transactional
    public static Meeting findMeeting(String PrimaryId) {
        if (PrimaryId == null || PrimaryId.length() == 0) return null;
        return entityManager().find(Meeting.class, PrimaryId);
    }

	@Transactional
    public static List<Meeting> findMeetingEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Meeting o", Meeting.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Meeting attached = Meeting.findMeeting(this.PrimaryId);
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
    public Meeting merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Meeting merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static TypedQuery<Meeting> findMeetingsByMcreatedByEquals(String mcreatedBy) {
        if (mcreatedBy == null || mcreatedBy.length() == 0) throw new IllegalArgumentException("The mcreatedBy argument is required");
        EntityManager em = Meeting.entityManager();
        TypedQuery<Meeting> q = em.createQuery("SELECT o FROM Meeting AS o WHERE o.mcreatedBy = :mcreatedBy", Meeting.class);
        q.setParameter("mcreatedBy", mcreatedBy);
        return q;
    }

	public static TypedQuery<Meeting> findMeetingsByMeetingIDEquals(String meetingID) {
        if (meetingID == null || meetingID.length() == 0) throw new IllegalArgumentException("The meetingID argument is required");
        EntityManager em = Meeting.entityManager();
        TypedQuery<Meeting> q = em.createQuery("SELECT o FROM Meeting AS o WHERE o.meetingID = :meetingID", Meeting.class);
        q.setParameter("meetingID", meetingID);
        return q;
    }
}
