package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.BestsellerOptionalProductEntity;
import it.polimi.db2_project.TELCOEJB.entities.InsolventUsersEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BestsellerOptionalProductService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

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

        return bestsellerOptionalProduct;
    }
}