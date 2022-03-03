package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "servicepackage")
public class ServicePackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId", nullable = false)
    private int packageId;

    @Column(name = "ValidityPeriod", nullable = false)
    private int validityPeriod;

    @Column(name = "MonthlyFee", nullable = false)
    private float monthlyFee;

    @Column(name = "Name", nullable = false, length =64)
    private String name;

    @OneToMany(mappedBy = "servicePackage", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST} )
    List<OrderEntity> orderEntities ;

    @ManyToMany(fetch = FetchType.EAGER )
    List<ServiceEntity> services;

}
