package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.AverageSalesOptionalProductPerServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AverageSalesOptionalProductPerServicePackageService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves the list of the average sales of the optional product per service package
     * @return the list of entities containing the average sales of the optional product per service package
     * @throws AdminViewsException if an error occurs while trying to execute the query
     */
    public List<AverageSalesOptionalProductPerServicePackageEntity> getAverageSales() throws AdminViewsException{
        List<AverageSalesOptionalProductPerServicePackageEntity> sales;

        try{
            // retrieving the list of users that match with a given username and password
            sales = em.createNamedQuery("AverageSalesOptionalProductPerServicePackageEntity.getAverageSales", AverageSalesOptionalProductPerServicePackageEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }

        for(int i = 0; i < sales.size(); i++){
            em.refresh(sales.get(i));
            em.refresh(sales.get(i).getAssociatedPackage());
        }
        return new ArrayList<>(sales);
    }
}