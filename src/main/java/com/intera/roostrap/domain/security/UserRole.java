package com.intera.roostrap.domain.security;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "users_roles")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "users_roles", finders = { "findUserRolesByUser" })
@RooJson
public class UserRole {

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Role role;

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

	public static TypedQuery<UserRole> findUserRolesByUser(User user) {
        if (user == null) throw new IllegalArgumentException("The user argument is required");
        EntityManager em = UserRole.entityManager();
        TypedQuery<UserRole> q = em.createQuery("SELECT o FROM UserRole AS o WHERE o.user = :user", UserRole.class);
        q.setParameter("user", user);
        return q;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static UserRole fromJsonToUserRole(String json) {
        return new JSONDeserializer<UserRole>().use(null, UserRole.class).deserialize(json);
    }

	public static String toJsonArray(Collection<UserRole> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<UserRole> fromJsonArrayToUserRoles(String json) {
        return new JSONDeserializer<List<UserRole>>().use(null, ArrayList.class).use("values", UserRole.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new UserRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countUserRoles() {
        return findAllUserRoles().size();
    }

	@Transactional
    public static List<UserRole> findAllUserRoles() {
        return entityManager().createQuery("SELECT o FROM UserRole o", UserRole.class).getResultList();
    }

	@Transactional
    public static UserRole findUserRole(Long id) {
        if (id == null) return null;
        return entityManager().find(UserRole.class, id);
    }

	@Transactional
    public static List<UserRole> findUserRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UserRole o", UserRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            UserRole attached = UserRole.findUserRole(this.id);
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
    public UserRole merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UserRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public User getUser() {
        return this.user;
    }

	public void setUser(User user) {
        this.user = user;
    }

	public Role getRole() {
        return this.role;
    }

	public void setRole(Role role) {
        this.role = role;
    }
}
