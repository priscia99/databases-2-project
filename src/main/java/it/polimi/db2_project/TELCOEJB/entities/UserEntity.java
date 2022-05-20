package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")

@NamedQueries({
        @NamedQuery(name = "UserEntity.checkCredentials", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password"),
//        @NamedQuery(name = "UserEntity.findByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email")
})
public class UserEntity {

    @Id
    @Column(name = "username", nullable = false, length=64)
    private String username;

    @Column(name = "password", nullable = false, length=64)
    private String password;

    @Column(name = "email", nullable = false, length=64)
    private String email;

    @Column(name = "isInsolvent", nullable = false)
    private boolean isInsolvent;

    @Column(name = "FailedAttempts", nullable = false)
    private int failedAttempts;

    @OneToOne(mappedBy = "relatedUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AlertEntity alert;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST} )
    List<OrderEntity> orderEntities ;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST} )
    List<InsolventUsersEntity> associatedInsolventUser ;

    public UserEntity(){}

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isInsolvent = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInsolvent() {
        return isInsolvent;
    }

    public void setInsolvent(boolean insolvent) {
        isInsolvent = insolvent;
    }

    public AlertEntity getAlert() {
        return alert;
    }

    public void setAlert(AlertEntity alert) {
        this.alert = alert;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}