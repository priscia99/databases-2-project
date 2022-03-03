package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class ServicePackageEntityPK implements Serializable {

    private int packageId;
    private int validityPeriod;
    private float monthlyFee;

    public ServicePackageEntityPK() {}

    public ServicePackageEntityPK(int packageId, int validityPeriod, float monthlyFee) {
        this.packageId = packageId;
        this.validityPeriod = validityPeriod;
        this.monthlyFee = monthlyFee;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicePackageEntityPK that = (ServicePackageEntityPK) o;
        return packageId == that.packageId && validityPeriod == that.validityPeriod && Float.compare(that.monthlyFee, monthlyFee) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, validityPeriod, monthlyFee);
    }
}
