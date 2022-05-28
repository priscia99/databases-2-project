package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.TotalPurchasesPerPackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.TotalPurchasesPerPackageValidityPeriodEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TotalPurchasesPerPackageValidityPeriodService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public List<TotalPurchasesPerPackageValidityPeriodEntity> getTotalPurchasesPerPackageValidityPeriod() throws AdminViewsException{
        List<TotalPurchasesPerPackageValidityPeriodEntity> purchases;

        try{
            // retrieving the list of users that match with a given username and password
            purchases = em.createNamedQuery("TotalPurchasesPerPackageValidityPeriodEntity.getAllPurchasesPerPackageValidityPeriod", TotalPurchasesPerPackageValidityPeriodEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }
        for(int i = 0; i < purchases.size();i++){
            em.refresh(purchases.get(i).getAssociatedPeriod());
        }
        return new ArrayList<>(purchases);
    }
}