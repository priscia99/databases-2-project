package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;


@Entity
@Table(name = "service")
public class ServicePackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "ValidityPeriod", nullable = false)
    private int validityPeriod;

    @Column(name = "MonthlyFee", nullable = false)
    private int monthlyFee;

    @Column(name = "Name", nullable = false, length =45)
    private String name;

    //todo da modificare
    @Column(name = "ServiceId", nullable = false)
    private int serviceId;
}
