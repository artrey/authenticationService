package authenticationserver.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by igor on 23.02.17.
 */
@Entity
public class DomainParticipant {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private long domainId;

    @Column(updatable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private String publicKey;

    @Column(nullable = false, updatable = false)
    private long creatorUserId;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationTime;

    @Column(nullable = false)
    private Statuses status = Statuses.ACTIVE;


    public DomainParticipant() {}

    public DomainParticipant(long domainId, String name, String publicKey, long creatorUserId) {
        this.domainId = domainId;
        this.name = name;
        this.publicKey = publicKey;
        this.creatorUserId = creatorUserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public enum Statuses {
        ACTIVE, INACTIVE;
    }
}
