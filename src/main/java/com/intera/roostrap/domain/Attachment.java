package com.intera.roostrap.domain;

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

import com.google.appengine.api.datastore.Blob;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Attachment {

	@Id
    @PrimaryKey
    private String PrimaryId;
    
    private String attachmentID;
	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date acreatedOn = toNearestWholeHourA();
    
    private Blob adata;
	
    private Boolean adeleted;
    
    private String adesc;
    
    private String afileName;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date amodifiedOn = toNearestWholeHourA();

    private Blob athumbnail;
    
    private String atype;
	
    private String anoteID;
    
	private String aparentType;
	private String aparentID;
    
    static Date toNearestWholeHourA() {
   	 
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

	public static Attachment fromJsonToAttachment(String json) {
        return new JSONDeserializer<Attachment>().use(null, Attachment.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Attachment> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Attachment> fromJsonArrayToAttachments(String json) {
        return new JSONDeserializer<List<Attachment>>().use(null, ArrayList.class).use("values", Attachment.class).deserialize(json);
    }

	public String getPrimaryId() {
        return this.PrimaryId;
    }

	public void setPrimaryId(String PrimaryId) {
        this.PrimaryId = PrimaryId;
    }

	public String getAttachmentID() {
        return this.attachmentID;
    }

	public void setAttachmentID(String attachmentID) {
        this.attachmentID = attachmentID;
    }

	public Date getAcreatedOn() {
        return this.acreatedOn;
    }

	public void setAcreatedOn(Date acreatedOn) {
        this.acreatedOn = acreatedOn;
    }

	public Boolean getAdeleted() {
        return this.adeleted;
    }

	public void setAdeleted(Boolean adeleted) {
        this.adeleted = adeleted;
    }

	public String getAdesc() {
        return this.adesc;
    }

	public void setAdesc(String adesc) {
        this.adesc = adesc;
    }

	public String getAfileName() {
        return this.afileName;
    }

	public void setAfileName(String afileName) {
        this.afileName = afileName;
    }

	public Date getAmodifiedOn() {
        return this.amodifiedOn;
    }

	public void setAmodifiedOn(Date amodifiedOn) {
        this.amodifiedOn = amodifiedOn;
    }

	public String getAtype() {
        return this.atype;
    }

	public void setAtype(String atype) {
        this.atype = atype;
    }

	public String getAnoteID() {
        return this.anoteID;
    }

	public void setAnoteID(String anoteID) {
        this.anoteID = anoteID;
    }

	public String getAparentType() {
        return this.aparentType;
    }

	public void setAparentType(String aparentType) {
        this.aparentType = aparentType;
    }

	public String getAparentID() {
        return this.aparentID;
    }

	public void setAparentID(String aparentID) {
        this.aparentID = aparentID;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Attachment().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countAttachments() {
        return findAllAttachments().size();
    }

	@Transactional
    public static List<Attachment> findAllAttachments() {
        return entityManager().createQuery("SELECT o FROM Attachment o", Attachment.class).getResultList();
    }

	@Transactional
    public static Attachment findAttachment(String PrimaryId) {
        if (PrimaryId == null || PrimaryId.length() == 0) return null;
        return entityManager().find(Attachment.class, PrimaryId);
    }

	@Transactional
    public static List<Attachment> findAttachmentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Attachment o", Attachment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Attachment attached = Attachment.findAttachment(this.PrimaryId);
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
    public Attachment merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Attachment merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
