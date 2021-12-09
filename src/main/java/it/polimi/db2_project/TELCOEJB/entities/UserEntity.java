package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;

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


    public UserEntity(){}

    public UserEntity(int id, String username, String password, boolean isInsolvent){
        this.id = id;
        this.username = username;
        this.password = password;
        this.isInsolvent = isInsolvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}