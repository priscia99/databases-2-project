package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "average_sales_optionalproduct_per_servicepackage")
public class AverageSalesOptionalProductPerServicePackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId", nullable = false)
    private int packageId;

    @Column(name = "averageOptionalProducts", nullable = true)
    private int averageOptionalProducts;

    public AverageSalesOptionalProductPerServicePackageEntity() {
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getAverageOptionalProducts() {
        return averageOptionalProducts;
    }

    public void setAverageOptionalProducts(int averageOptionalProducts) {
        this.averageOptionalProducts = averageOptionalProducts;
    }
}
