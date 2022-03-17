package it.polimi.db2_project.TELCOEJB.entities;
import it.polimi.db2_project.TELCOEJB.enums.ServiceType;

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
    private ServiceType serviceType;

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

    public ServiceEntity(int serviceId, ServiceType serviceType, int includedMinutes, int feeMinutes, int includedSMS, int feeSMS, int includedGB, int feeGB) {
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        IncludedMinutes = includedMinutes;
        FeeMinutes = feeMinutes;
        IncludedSMS = includedSMS;
        FeeSMS = feeSMS;
        IncludedGB = includedGB;
        FeeGB = feeGB;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public int getIncludedMinutes() {
        return IncludedMinutes;
    }

    public void setIncludedMinutes(int includedMinutes) {
        IncludedMinutes = includedMinutes;
    }

    public int getFeeMinutes() {
        return FeeMinutes;
    }

    public void setFeeMinutes(int feeMinutes) {
        FeeMinutes = feeMinutes;
    }

    public int getIncludedSMS() {
        return IncludedSMS;
    }

    public void setIncludedSMS(int includedSMS) {
        IncludedSMS = includedSMS;
    }

    public int getFeeSMS() {
        return FeeSMS;
    }

    public void setFeeSMS(int feeSMS) {
        FeeSMS = feeSMS;
    }

    public int getIncludedGB() {
        return IncludedGB;
    }

    public void setIncludedGB(int includedGB) {
        IncludedGB = includedGB;
    }

    public int getFeeGB() {
        return FeeGB;
    }

    public void setFeeGB(int feeGB) {
        FeeGB = feeGB;
    }

    public List<ServicePackageEntity> getServicePackageEntities() {
        return servicePackageEntities;
    }

    public void setServicePackageEntities(List<ServicePackageEntity> servicePackageEntities) {
        this.servicePackageEntities = servicePackageEntities;
    }
}
