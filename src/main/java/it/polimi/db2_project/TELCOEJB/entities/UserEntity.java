package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")

@NamedQueries({
        @NamedQuery(name = "UserEntity.checkCredentials", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String username;
    private String password;
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
}