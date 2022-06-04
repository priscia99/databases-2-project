package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "total_purchases_per_package_validityperiod")
@NamedQueries({
        @NamedQuery(name = "TotalPurchasesPerPackageValidityPeriodEntity.getAllPurchasesPerPackageValidityPeriod", query = "SELECT p FROM TotalPurchasesPerPackageValidityPeriodEntity p"),
})
public class TotalPurchasesPerPackageValidityPeriodEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "periodId")
    private PeriodEntity associatedPeriod;

    @Column(name = "total_purchases", nullable = true)
    private int totalPurchases;

    public TotalPurchasesPerPackageValidityPeriodEntity() {
    }

    public PeriodEntity getAssociatedPeriod() {
        return associatedPeriod;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
}
