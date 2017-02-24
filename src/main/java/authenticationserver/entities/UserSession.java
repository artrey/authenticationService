package authenticationserver.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by igor on 23.02.17.
 */
@Entity
public class UserSession {

    @Id
    private String sessionCode;

    @Column(unique = true, updatable = false, nullable = false)
    private long userId;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;


    public UserSession() {}


    public UserSession(String sessionCode, long userId) {
        this.sessionCode = sessionCode;
        this.userId = userId;
    }


    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
