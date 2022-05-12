package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "period")
@NamedQueries({
        @NamedQuery(name = "PeriodEntity.findPeriodById", query = "SELECT p FROM PeriodEntity p WHERE p.periodId = :periodId"),
})
public class PeriodEntity {

    // columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int periodId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validityPeriod")
    private int validityPeriod;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthlyFee")
    private float monthlyFee;

    // foreign keys
    @ManyToOne()
    @JoinColumn(name = "packageId")
    private ServicePackageEntity servicePackage;    // on service package table

    @OneToMany(mappedBy = "periodId", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST} )
    List<OrderEntity> orders;   // on order table

    // constructors
    public PeriodEntity(int validityPeriod, float monthlyFee, ServicePackageEntity servicePackage) {
        this.validityPeriod = validityPeriod;
        this.monthlyFee = monthlyFee;
        this.servicePackage = servicePackage;
    }

    public PeriodEntity() {}

    // getter
    public int getPeriodId() {
        return periodId;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public ServicePackageEntity getServicePackage() {
        return servicePackage;
    }

    // setters
    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public void setServicePackage(ServicePackageEntity servicePackage) {
        this.servicePackage = servicePackage;
    }
}
