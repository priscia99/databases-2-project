package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "insolvent_users")
@NamedQueries({
        @NamedQuery(name = "InsolventUsersEntity.getInsolventUsers", query = "SELECT DISTINCT u FROM InsolventUsersEntity u"),
})
public class InsolventUsersEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private UserEntity user;

    public InsolventUsersEntity() {
    }

    public UserEntity getUser() {
        return user;
    }
}
