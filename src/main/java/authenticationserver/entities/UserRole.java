package authenticationserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by igor on 25.02.17.
 */
@Entity
public class UserRole {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private long userId;

    @Column(nullable = false, updatable = false)
    private long organizationId;

    @Column(updatable = false)
    private Long domainId;

    @Column(nullable = false, updatable = false)
    private Roles role;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date grantTime;

    @Column(nullable = false, updatable = false)
    private long granterUserId;

    @Column
    private Date denyTime;

    @Column
    private Long denierUserId;


    public UserRole() {}


    public UserRole(long userId, long organizationId, Long domainId, Roles role, long granterUserId)
    {
        this.userId = userId;
        this.organizationId = organizationId;
        this.domainId = domainId;
        this.role = role;
        this.granterUserId = granterUserId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public long getGranterUserId() {
        return granterUserId;
    }

    public void setGranterUserId(long granterUserId) {
        this.granterUserId = granterUserId;
    }

    public Date getDenyTime() {
        return denyTime;
    }

    public void setDenyTime(Date denyTime) {
        this.denyTime = denyTime;
    }

    public Long getDenierUserId() {
        return denierUserId;
    }

    public void setDenierUserId(Long denierUserId) {
        this.denierUserId = denierUserId;
    }

    public enum Roles {
        ADMINISTRATOR, DISTRIBUTOR, AUTHENTICATOR;
    }

}
