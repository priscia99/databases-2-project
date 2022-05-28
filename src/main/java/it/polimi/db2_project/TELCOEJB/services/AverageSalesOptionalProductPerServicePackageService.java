package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.AverageSalesOptionalProductPerServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.TotalPurchasesPerPackageEntity;
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

        return new ArrayList<>(sales);
    }
}