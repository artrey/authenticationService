package authenticationserver.entities;

import authenticationserver.swagger.model.MOrganization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by igor on 23.02.17.
 */
@Entity
public class Organization {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, updatable = false)
    private long creatorUserId;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Column(nullable = false)
    private Statuses status = Statuses.ACTIVE;


    public Organization() {}


    public Organization(String name, String description, long creatorUserId)
    {
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

    public void setCreatorUserId(long creatorUserId)
    {
        this.creatorUserId = creatorUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }


    public MOrganization toMOrganization()
    {
        MOrganization o = new MOrganization();
        o.setId(id);
        o.setName(name);
        o.setDescription(description);
        if (status == Statuses.ACTIVE) o.setStatus(MOrganization.StatusEnum.ACTIVE);
        else if (status == Statuses.INACTIVE) o.setStatus(MOrganization.StatusEnum.INACTIVE);

        return o;
    }

    public enum Statuses {
        ACTIVE, INACTIVE;
    }
}
