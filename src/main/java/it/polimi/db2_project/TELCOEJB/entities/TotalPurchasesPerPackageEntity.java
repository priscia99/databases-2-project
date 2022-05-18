package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "total_purchases_per_package")

@NamedQueries({
        @NamedQuery(name = "TotalPurchasesPerPackageEntity.getAllPurchasesPerPackage", query = "SELECT p FROM TotalPurchasesPerPackageEntity p"),
})
public class TotalPurchasesPerPackageEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packageId")
    private ServicePackageEntity associatedPackage;

    @Column(name = "total_purchases", nullable = true)
    private int totalPurchases;

    public TotalPurchasesPerPackageEntity() {
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public ServicePackageEntity getAssociatedPackage() {
        return associatedPackage;
    }
}
