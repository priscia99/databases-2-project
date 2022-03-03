package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;


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

    @ManyToMany(mappedBy = "optionalProducts", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<OrderEntity> orderEntities ;

    public OptionalProductEntity() {
    }

    public OptionalProductEntity(int productId, String name, int monthlyFee, List<OrderEntity> orderEntities) {
        this.productId = productId;
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.orderEntities = orderEntities;
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

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}
