package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;
import java.util.List;


@Entity
@IdClass(ServicePackageEntityPK.class)
@Table(name = "servicepackage")

@NamedQueries({
        @NamedQuery(name = "ServicePackageEntity.getAllPackages", query = "SELECT p FROM ServicePackageEntity p"),
        @NamedQuery(name = "ServicePackageEntity.getPackagesById", query = "SELECT p FROM ServicePackageEntity p WHERE p.packageId = :packageId"),
        @NamedQuery(name = "ServicePackageEntity.getPackagesByIdAndValidityPeriod", query = "SELECT p FROM ServicePackageEntity p WHERE p.packageId = :packageId and p.validityPeriod = :validityPeriod")

})
public class ServicePackageEntity {

    @Id
    @Column(name = "packageId")
    private int packageId;

    @Id
    @Column(name = "validityPeriod")
    private int validityPeriod;

    @Id
    @Column(name = "monthlyFee")
    private float monthlyFee;

    @Column(name = "Name", nullable = false, length =64)
    private String name;

    @OneToMany(mappedBy = "servicePackage", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST} )
    List<OrderEntity> orderEntities ;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "servicepackage_service",
    joinColumns = {
                    @JoinColumn(name = "servicepackage_id", referencedColumnName = "packageId"),
            @JoinColumn(name = "servicepackage_validityperiod", referencedColumnName = "validityPeriod"),
            @JoinColumn(name = "servicepackage_monthlyfee", referencedColumnName = "monthlyFee")
    },
    inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "serviceId")})
    List<ServiceEntity> services;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "servicepackage_optionalproduct",
            joinColumns = {
                    @JoinColumn(name = "servicepackage_id", referencedColumnName = "packageId"),
       },
            inverseJoinColumns = {@JoinColumn(name = "optionalproduct_id", referencedColumnName = "productId")})
    List<OptionalProductEntity> optionalProducts;

    public ServicePackageEntity() {
    }

    public ServicePackageEntity(int packageId, int validityPeriod, float monthlyFee, String name, List<OrderEntity> orderEntities, List<ServiceEntity> services, List<OptionalProductEntity> optionalProducts) {
        this.packageId = packageId;
        this.validityPeriod = validityPeriod;
        this.monthlyFee = monthlyFee;
        this.name = name;
        this.orderEntities = orderEntities;
        this.services = services;
        this.optionalProducts = optionalProducts;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    public List<OptionalProductEntity> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProductEntity> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }
}
