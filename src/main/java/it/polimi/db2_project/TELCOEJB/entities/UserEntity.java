package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")

@NamedQueries({
        @NamedQuery(name = "UserEntity.checkCredentials", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
})
public class UserEntity {

    @Id
    @Column(name = "username", nullable = false, length=64)
    private String username;

    @Column(name = "password", nullable = false, length=64)
    private String password;

    @Column(name = "isInsolvent", nullable = false)
    private boolean isInsolvent;

    @OneToOne(mappedBy = "relatedUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AlertEntity alert;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST} )
    List<OrderEntity> orderEntities ;

    public UserEntity(){}

    public UserEntity(String username, String password, boolean isInsolvent, AlertEntity alert, List<OrderEntity> orderEntities) {
        this.username = username;
        this.password = password;
        this.isInsolvent = isInsolvent;
        this.alert = alert;
        this.orderEntities = orderEntities;
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

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}