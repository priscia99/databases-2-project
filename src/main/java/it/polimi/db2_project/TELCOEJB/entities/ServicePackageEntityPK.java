package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ServicePackageEntityPK implements Serializable {

    private int productId;
    private int validityPeriod;
    private float monthlyFee;

    public ServicePackageEntityPK() {}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
}
