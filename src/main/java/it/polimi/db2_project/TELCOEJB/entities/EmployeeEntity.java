package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
@NamedQueries({
        @NamedQuery(name = "EmployeeEntity.checkCredentials", query = "SELECT e FROM EmployeeEntity e WHERE e.id = :id AND e.password = :password")
})

public class EmployeeEntity {

    @Id
    @Column(name = "id", nullable = false, length=64)
    private String id;

    @Column(name = "password", nullable = false, length=64)
    private String password;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}