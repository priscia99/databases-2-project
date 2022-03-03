package it.polimi.db2_project.TELCOEJB.entities;
import javax.persistence.*;


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

    public ServiceEntity() {

    }

    public ServiceEntity(int serviceId, String serviceType, int includedMinutes, int feeMinutes, int includedSMS, int feeSMS, int includedGB, int feeGB) {
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
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
}
