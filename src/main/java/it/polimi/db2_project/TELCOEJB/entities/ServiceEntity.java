package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId", nullable = false)
    private int serviceId;

    @Column(name = "serviceType", nullable = false)
    @Enumerated(EnumType.STRING)
    private String serviceType;

    @Column(name = "includedMinutes", nullable = true)
    private int IncludedMinutes;

    @Column(name = "feeMinutes", nullable = true)
    private int FeeMinutes;

    @Column(name = "includedSms", nullable = true)
    private int IncludedSMS;

    @Column(name = "feeSms", nullable = true)
    private int FeeSMS;

    @Column(name = "includedGb", nullable = true)
    private int IncludedGB;

    @Column(name = "feeGb", nullable = true)
    private int FeeGB;

    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    List<ServicePackageEntity> servicePackageEntities ;


    public ServiceEntity() {

    }

}
