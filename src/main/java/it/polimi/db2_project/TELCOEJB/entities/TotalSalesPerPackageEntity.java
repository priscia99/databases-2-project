package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "total_sales_per_package")
@NamedQueries({
        @NamedQuery(name = "TotalSalesPerPackageEntity.getTotalSalesPerPackage", query = "SELECT s FROM TotalSalesPerPackageEntity s"),
})
public class TotalSalesPerPackageEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packageId")
    private ServicePackageEntity associatedPackage;

    @Column(name = "totalsales", nullable = true)
    private int totalSales;


    @Column(name = "totalsalesWithOptionalProduct", nullable = true)
    private int totalSalesWithOptionalProduct;

    public TotalSalesPerPackageEntity() {
    }

    public ServicePackageEntity getAssociatedPackage() {
        return associatedPackage;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getTotalSalesWithOptionalProduct() {
        return totalSalesWithOptionalProduct;
    }

    public void setTotalSalesWithOptionalProduct(int totalSalesWithOptionalProduct) {
        this.totalSalesWithOptionalProduct = totalSalesWithOptionalProduct;
    }
}
