package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "average_sales_optionalproduct_per_servicepackage")
@NamedQueries({
        @NamedQuery(name = "AverageSalesOptionalProductPerServicePackageEntity.getAverageSales", query = "SELECT s FROM AverageSalesOptionalProductPerServicePackageEntity s"),
})
public class AverageSalesOptionalProductPerServicePackageEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packageId")
    private ServicePackageEntity associatedPackage;

    @Column(name = "averageOptionalProducts", nullable = true)
    private float averageOptionalProducts;

    public AverageSalesOptionalProductPerServicePackageEntity() {
    }

    public ServicePackageEntity getAssociatedPackage() {
        return associatedPackage;
    }

    public float getAverageOptionalProducts() {
        return averageOptionalProducts;
    }

    public void setAverageOptionalProducts(int averageOptionalProducts) {
        this.averageOptionalProducts = averageOptionalProducts;
    }
}
