package authenticationserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by igor on 28.02.17.
 */
@Entity
public class WhiteOrganization {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private long uId;

    @Column(nullable = false, updatable = false)
    private long oId;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date addTime;

    @Column
    private Date remTime;

    public WhiteOrganization() {}

    public WhiteOrganization(long uId, long oId)
    {
        this.uId = uId;
        this.oId = oId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public long getoId() {
        return oId;
    }

    public void setoId(long oId) {
        this.oId = oId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getRemTime() {
        return remTime;
    }

    public void setRemTime(Date remTime) {
        this.remTime = remTime;
    }
}
