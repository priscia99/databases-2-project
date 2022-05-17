package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "total_sales_per_package")
public class TotalSalesPerPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId", nullable = false)
    private int packageId;

    @Column(name = "totalsales", nullable = true)
    private int totalSales;


    @Column(name = "totalsalesWithOptionalProduct", nullable = true)
    private int totalSalesWithOptionalProduct;

    public TotalSalesPerPackageEntity() {
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
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
