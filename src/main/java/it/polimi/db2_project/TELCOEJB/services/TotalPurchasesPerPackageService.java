package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.OptionalProductEntity;
import it.polimi.db2_project.TELCOEJB.entities.TotalPurchasesPerPackageEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;
import it.polimi.db2_project.TELCOEJB.exceptions.OptionalProductException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TotalPurchasesPerPackageService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves the list of total purchases per service package
     * @return the list of entities containing the total purchases per service package
     * @throws AdminViewsException if an error occurs while trying to execute the query
     */
    public List<TotalPurchasesPerPackageEntity> getTotalPurchasesPerPackage() throws AdminViewsException{
        List<TotalPurchasesPerPackageEntity> purchases;

        try{
            // retrieving the list of users that match with a given username and password
            purchases = em.createNamedQuery("TotalPurchasesPerPackageEntity.getAllPurchasesPerPackage", TotalPurchasesPerPackageEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }
        for(int i = 0; i < purchases.size(); i++){
            em.refresh(purchases.get(i));
            em.refresh(purchases.get(i).getAssociatedPackage());
        }

        return new ArrayList<>(purchases);
    }
}