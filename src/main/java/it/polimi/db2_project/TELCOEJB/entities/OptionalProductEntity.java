package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "optionalproduct")


@NamedQueries({
        @NamedQuery(name = "OptionalProductsEntity.getAllOptionalProducts", query = "SELECT o FROM OptionalProductEntity o"),
        @NamedQuery(name = "OptionalProductsEntity.getOptionalProductsByIdAndMonthlyFee", query = "SELECT o FROM OptionalProductEntity o WHERE o.productId = :productId and o.monthlyFee = :monthlyFee"),
        @NamedQuery(name = "OptionalProductsEntity.getOptionalProductsById", query = "SELECT o FROM OptionalProductEntity o WHERE o.productId = :productId"),

})
public class OptionalProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID", nullable = false)
    private int productId;

    @Column(name = "name", nullable = false, length =25)
    private String name;

    @Column(name = "monthlyFee", nullable = false)
    private int monthlyFee;

    @ManyToMany(mappedBy = "optionalProducts", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<OrderEntity> orderEntities ;

    @ManyToMany(mappedBy = "optionalProducts", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    List<ServicePackageEntity> servicePackageEntities ;


    public OptionalProductEntity() {
    }

    public OptionalProductEntity(int productId, String name, int monthlyFee, List<OrderEntity> orderEntities, List<ServicePackageEntity> servicePackageEntities) {
        this.productId = productId;
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.orderEntities = orderEntities;
        this.servicePackageEntities = servicePackageEntities;
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

    public List<ServicePackageEntity> getServicePackageEntities() {
        return servicePackageEntities;
    }

    public void setServicePackageEntities(List<ServicePackageEntity> servicePackageEntities) {
        this.servicePackageEntities = servicePackageEntities;
    }
}
