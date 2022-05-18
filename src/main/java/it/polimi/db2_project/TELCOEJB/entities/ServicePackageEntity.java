package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;
import java.time.Period;
import java.util.List;


@Entity
@Table(name = "servicepackage")

@NamedQueries({
        @NamedQuery(name = "ServicePackageEntity.getAllPackages", query = "SELECT p FROM ServicePackageEntity p"),
        @NamedQuery(name = "ServicePackageEntity.getPackagesById", query = "SELECT p FROM ServicePackageEntity p WHERE p.packageId = :packageId"),
        // @NamedQuery(name = "ServicePackageEntity.getPackagesByIdAndValidityPeriod", query = "SELECT p FROM ServicePackageEntity p WHERE p.packageId = :packageId and p.validityPeriod = :validityPeriod")

})
public class ServicePackageEntity {

    // attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId")
    private int packageId;

    @Column(name = "Name", nullable = false, length =64)
    private String name;

    // foreign keys
    @OneToMany(mappedBy = "servicePackage", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST} )
    List<PeriodEntity> periods;


    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "servicepackage_service",
    joinColumns = {
                    @JoinColumn(name = "servicepackage_id", referencedColumnName = "packageId"),
    },
    inverseJoinColumns = {@JoinColumn(name = "service_id", referencedColumnName = "serviceId")})
    List<ServiceEntity> services;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "servicepackage_optionalproduct",
            joinColumns = {
                    @JoinColumn(name = "servicepackage_id", referencedColumnName = "packageId"),
       },
            inverseJoinColumns = {@JoinColumn(name = "optionalproduct_productID", referencedColumnName = "productId")})
    List<OptionalProductEntity> optionalProducts;

    @OneToMany(mappedBy = "associatedPackage", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST} )
    List<TotalPurchasesPerPackageEntity> associatedTotalPurchasesPerPackage;

    public ServicePackageEntity() {
    }

    public ServicePackageEntity(String name, List<ServiceEntity> services, List<OptionalProductEntity> optionalProducts) {
        this.name = name;
        this.services = services;
        this.optionalProducts = optionalProducts;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<PeriodEntity> getPeriods() {
        return periods;
    }
}
