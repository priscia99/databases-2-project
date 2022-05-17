package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "total_purchases_per_package_validityperiod")
public class TotalPurchasesPerPackageValidityPeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "periodID", nullable = false)
    private int periodID;

    @Column(name = "total_purchases", nullable = true)
    private int totalPurchases;

    public TotalPurchasesPerPackageValidityPeriodEntity() {
    }

    public int getPeriodID() {
        return periodID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
}
