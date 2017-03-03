package authenticationserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by igor on 26.02.17.
 */
@Entity
public class ParticipantAuthentication {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private long participantId;

    @Column(nullable = false, updatable = false)
    private long value;

    @Column(updatable = false)
    private String error;

    @Column(updatable = false, nullable = false)
    private long authenticatorId;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date authTime;


    public ParticipantAuthentication() {}


    public ParticipantAuthentication(long participantId, long value, String error, long authenticatorId)
    {
        this.participantId = participantId;
        this.value = value;
        this.error = error;
        this.authenticatorId = authenticatorId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
