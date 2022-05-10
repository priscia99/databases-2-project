package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alert")
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alertID", nullable = false)
    private int alertID;

    @Column(name = "amount", nullable = true)
    private float amount;

    @Column(name = "lastRejectionDateTime", nullable = true)
    private Timestamp lastRejectionDateTime;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "username", referencedColumnName = "username"),
            @JoinColumn(name = "email", referencedColumnName = "email")
    })
    private UserEntity relatedUser;


    public AlertEntity(){}

    public AlertEntity(float amount, Timestamp lastRejectionDateTime, UserEntity relatedUser) {
        this.amount = amount;
        this.lastRejectionDateTime = lastRejectionDateTime;
        this.relatedUser = relatedUser;
    }

    public int getAlertID() {
        return alertID;
    }

    public void setAlertID(int alertID) {
        this.alertID = alertID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Timestamp getLastRejectionDateTime() {
        return lastRejectionDateTime;
    }

    public void setLastRejectionDateTime(Timestamp lastRejectionDateTime) {
        this.lastRejectionDateTime = lastRejectionDateTime;
    }

    public UserEntity getRelatedUser() {
        return relatedUser;
    }

    public void setRelatedUser(UserEntity relatedUser) {
        this.relatedUser = relatedUser;
    }
}
