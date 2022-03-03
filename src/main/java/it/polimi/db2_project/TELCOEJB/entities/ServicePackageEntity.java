package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;
import java.util.List;


@Entity
@IdClass(ServicePackageEntityPK.class)
@Table(name = "servicepackage")
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
    List<ServiceEntity> services;

}
