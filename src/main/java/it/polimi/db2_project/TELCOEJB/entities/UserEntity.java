package it.polimi.db2_project.TELCOEJB.entities;

public class UserEntity {
    int id;
    String username;

    public UserEntity(){}

    public UserEntity(int id, String username){
        this.id = id;
        this.username = username;
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