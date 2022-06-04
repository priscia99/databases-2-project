package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.BestsellerOptionalProductEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Stateless
public class BestsellerOptionalProductService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves the best seller optional product
     * @return the entity containing the best seller optional product
     * @throws AdminViewsException if an error occurs while trying to execute the query
     */
    public BestsellerOptionalProductEntity getBestsellerProduct() throws AdminViewsException{
        BestsellerOptionalProductEntity bestsellerOptionalProduct;

        try{
            // retrieving the list of users that match with a given username and password
            bestsellerOptionalProduct = em.find(BestsellerOptionalProductEntity.class, 1);
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }
        if(bestsellerOptionalProduct.getOptionalProduct() != null) {
            em.refresh(bestsellerOptionalProduct.getOptionalProduct());
        }
        return bestsellerOptionalProduct;
    }
}