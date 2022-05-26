package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "period")
//@NamedQueries({
//        @NamedQuery(name = "PeriodEntity.findPeriodById", query = "SELECT p FROM PeriodEntity p WHERE p.periodId = :periodId"),
//})
public class PeriodEntity {

    // columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int periodId;

    @Column(name = "validityPeriod")
    private int validityPeriod;

    @Column(name = "monthlyFee")
    private float monthlyFee;

    // foreign keys
    @ManyToOne()
    @JoinColumn(name = "packageId")
    private ServicePackageEntity servicePackage;    // on service package table

    @OneToMany(mappedBy = "associatedPeriod", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST} )
    List<OrderEntity> orders;   // on order table

    public PeriodEntity() {
    }

    public PeriodEntity(int validityPeriod, float monthlyFee, ServicePackageEntity servicePackage, List<OrderEntity> orders) {
        this.validityPeriod = validityPeriod;
        this.monthlyFee = monthlyFee;
        this.servicePackage = servicePackage;
        this.orders = orders;
    }

    public PeriodEntity(int validityPeriod, float monthlyFee, ServicePackageEntity servicePackage) {
        this.validityPeriod = validityPeriod;
        this.monthlyFee = monthlyFee;
        this.servicePackage = servicePackage;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public ServicePackageEntity getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackageEntity servicePackage) {
        this.servicePackage = servicePackage;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
