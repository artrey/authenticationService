package authenticationserver.entities;

import authenticationserver.swagger.model.MDomain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by igor on 23.02.17.
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"organizationId", "name"})})
public class Domain {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private long organizationId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, updatable = false)
    private long creatorUserId;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationTime;

    @Column(nullable = false)
    private Statuses status = Statuses.ACTIVE;


    public Domain() {}


    public Domain(long organizationId, String name, String description, long creatorUserId)
    {
        this.organizationId = organizationId;
        this.name = name;
        this.description = description;
        this.creatorUserId = creatorUserId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public MDomain toMDomain()
    {
        MDomain d = new MDomain();
        d.setId(id);
        d.setName(name);
        d.setDescription(description);
        if (status == Statuses.ACTIVE) d.setStatus(MDomain.StatusEnum.ACTIVE);
        else if (status == Statuses.INACTIVE) d.setStatus(MDomain.StatusEnum.INACTIVE);

        return d;
    }

    public enum Statuses {
        ACTIVE, INACTIVE;
    }
}
