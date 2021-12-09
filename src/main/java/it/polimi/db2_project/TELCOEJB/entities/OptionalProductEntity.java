package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;


@Entity
@Table(name = "optionalproduct")
public class OptionalProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId", nullable = false)
    private int productId;

    @Column(name = "Name", nullable = false, length =25)
    private String name;

    @Column(name = "MonthlyFee", nullable = false)
    private int monthlyFee;

    public OptionalProductEntity() {
    }

    public OptionalProductEntity(int productId, String name, int monthlyFee) {
        this.productId = productId;
        this.name = name;
        this.monthlyFee = monthlyFee;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
