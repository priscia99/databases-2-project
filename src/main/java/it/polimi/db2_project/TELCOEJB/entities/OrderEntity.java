package it.polimi.db2_project.TELCOEJB.entities;

import it.polimi.db2_project.TELCOEJB.enums.OrderState;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.List;

@Entity
@Table(name = "order")

public class OrderEntity {

    @Id
    @Column(name = "orderId", nullable = false, length=64)
    private String orderId;

    @Column(name = "creationDateTime", nullable = false)
    private Timestamp creationDateTime;

    @Column(name = "totalFee", nullable = false)
    private float totalFee;

    @Column(name = "startTime", nullable = false)
    private Timestamp startTime;

    @Column(name = "endTime", nullable = false)
    private Timestamp endTime;

    @Column(name = "orderState", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "username")
    UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER )
    List<OptionalProductEntity> optionalProducts ;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "packageID", referencedColumnName = "packageID"),
            @JoinColumn(name = "validityPeriod", referencedColumnName = "validityPeriod"),
            @JoinColumn(name = "monthlyFee", referencedColumnName = "monthlyFee")
    })
    ServicePackageEntity servicePackage;


    public OrderEntity(){}

    public OrderEntity(String orderId, Timestamp creationDateTime, float totalFee, Timestamp startTime, Timestamp endTime, OrderState orderState, UserEntity user, List<OptionalProductEntity> optionalProducts, ServicePackageEntity servicePackage) {
        this.orderId = orderId;
        this.creationDateTime = creationDateTime;
        this.totalFee = totalFee;
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderState = orderState;
        this.user = user;
        this.optionalProducts = optionalProducts;
        this.servicePackage = servicePackage;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Timestamp creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<OptionalProductEntity> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProductEntity> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public ServicePackageEntity getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackageEntity servicePackage) {
        this.servicePackage = servicePackage;
    }
}
