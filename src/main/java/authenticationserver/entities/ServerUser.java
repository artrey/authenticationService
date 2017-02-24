package authenticationserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by igor on 23.02.17.
 */
@Entity
public class ServerUser {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String login;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false, unique = true)
    private String passHash;

    @Column(nullable = false, updatable = false)
    private byte[] passSalt;

    @Column(updatable = false, nullable = false, insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creationTime;

    @Column(nullable = false)
    private Statuses status = Statuses.ACTIVE;

    public ServerUser() {}

    public ServerUser(String login, String email, byte[] passSalt, String passHash) {
        this.login = login;
        this.email = email;
        this.passSalt = passSalt;
        this.passHash = passHash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public byte[] getPassSalt()
    {
        return passSalt;
    }

    public void setPassSalt(byte[] passSalt)
    {
        this.passSalt = passSalt;
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
