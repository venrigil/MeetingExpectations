package com.intera.roostrap.domain.security;

import com.intera.roostrap.domain.security.attribute.CreateAttributes;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "users")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "users", finders = { "findUsersByEmailAddress" })
@RooJson
public class User {

    @NotNull
    @Size(min = 1)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1)
    @Column(name = "last_name")
    private String lastName;

    @PrimaryKey
    @NotNull
    @Size(min = 1)
    @Column(name = "email_address")
    private String emailAddress;

    @NotNull(groups = CreateAttributes.class)
    @Size(min = 1)
    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(name = "activation_date")
    private Date activationDate;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "locked")
    private Boolean locked;

    private String uactivationDateString;

    private String umeetings;

    private String uagendaItems;

    private String uparticipants;

    private String pnotes;

    private String uactionItems;

    private String uWebCreated;

    private String uiPadAppCreated;

    private String uAndroidCreated;

    private String uOtherCreated;

    private String uAddlColumn1;

    private String uAddlColumn2;

    private String uAddlColumn3;

    private String uAddlColumn4;

    private String uAddlColumn5;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getEmailAddress() {
        return this.emailAddress;
    }

	public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public Date getActivationDate() {
        return this.activationDate;
    }

	public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

	public Boolean getEnabled() {
        return this.enabled;
    }

	public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

	public Boolean getLocked() {
        return this.locked;
    }

	public void setLocked(Boolean locked) {
        this.locked = locked;
    }

	public String getUactivationDateString() {
        return this.uactivationDateString;
    }

	public void setUactivationDateString(String uactivationDateString) {
        this.uactivationDateString = uactivationDateString;
    }

	public String getUmeetings() {
        return this.umeetings;
    }

	public void setUmeetings(String umeetings) {
        this.umeetings = umeetings;
    }

	public String getUagendaItems() {
        return this.uagendaItems;
    }

	public void setUagendaItems(String uagendaItems) {
        this.uagendaItems = uagendaItems;
    }

	public String getUparticipants() {
        return this.uparticipants;
    }

	public void setUparticipants(String uparticipants) {
        this.uparticipants = uparticipants;
    }

	public String getPnotes() {
        return this.pnotes;
    }

	public void setPnotes(String pnotes) {
        this.pnotes = pnotes;
    }

	public String getUactionItems() {
        return this.uactionItems;
    }

	public void setUactionItems(String uactionItems) {
        this.uactionItems = uactionItems;
    }

	public String getUWebCreated() {
        return this.uWebCreated;
    }

	public void setUWebCreated(String uWebCreated) {
        this.uWebCreated = uWebCreated;
    }

	public String getUiPadAppCreated() {
        return this.uiPadAppCreated;
    }

	public void setUiPadAppCreated(String uiPadAppCreated) {
        this.uiPadAppCreated = uiPadAppCreated;
    }

	public String getUAndroidCreated() {
        return this.uAndroidCreated;
    }

	public void setUAndroidCreated(String uAndroidCreated) {
        this.uAndroidCreated = uAndroidCreated;
    }

	public String getUOtherCreated() {
        return this.uOtherCreated;
    }

	public void setUOtherCreated(String uOtherCreated) {
        this.uOtherCreated = uOtherCreated;
    }

	public String getUAddlColumn1() {
        return this.uAddlColumn1;
    }

	public void setUAddlColumn1(String uAddlColumn1) {
        this.uAddlColumn1 = uAddlColumn1;
    }

	public String getUAddlColumn2() {
        return this.uAddlColumn2;
    }

	public void setUAddlColumn2(String uAddlColumn2) {
        this.uAddlColumn2 = uAddlColumn2;
    }

	public String getUAddlColumn3() {
        return this.uAddlColumn3;
    }

	public void setUAddlColumn3(String uAddlColumn3) {
        this.uAddlColumn3 = uAddlColumn3;
    }

	public String getUAddlColumn4() {
        return this.uAddlColumn4;
    }

	public void setUAddlColumn4(String uAddlColumn4) {
        this.uAddlColumn4 = uAddlColumn4;
    }

	public String getUAddlColumn5() {
        return this.uAddlColumn5;
    }

	public void setUAddlColumn5(String uAddlColumn5) {
        this.uAddlColumn5 = uAddlColumn5;
    }

	public static TypedQuery<User> findUsersByEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.length() == 0) throw new IllegalArgumentException("The emailAddress argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.emailAddress = :emailAddress", User.class);
        q.setParameter("emailAddress", emailAddress);
        return q;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new User().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countUsers() {
        return findAllUsers().size();
    }

	@Transactional
    public static List<User> findAllUsers() {
        return entityManager().createQuery("SELECT o FROM User o", User.class).getResultList();
    }

	@Transactional
    public static User findUser(Long id) {
        if (id == null) return null;
        return entityManager().find(User.class, id);
    }

	@Transactional
    public static List<User> findUserEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM User o", User.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            User attached = User.findUser(this.id);
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
    public User merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        User merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static User fromJsonToUser(String json) {
        return new JSONDeserializer<User>().use(null, User.class).deserialize(json);
    }

	public static String toJsonArray(Collection<User> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<User> fromJsonArrayToUsers(String json) {
        return new JSONDeserializer<List<User>>().use(null, ArrayList.class).use("values", User.class).deserialize(json);
    }
}
