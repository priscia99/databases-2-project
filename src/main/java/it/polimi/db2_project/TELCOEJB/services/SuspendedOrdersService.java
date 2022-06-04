package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.SuspendedOrdersEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class SuspendedOrdersService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves the list of suspended orders
     * @return the list of entities containing the suspended orders
     * @throws AdminViewsException if an error occurs while trying to execute the query
     */
    public List<SuspendedOrdersEntity> getSuspendedOrders() throws AdminViewsException{
        List<SuspendedOrdersEntity> orders;

        try{
            // retrieving the list of users that match with a given username and password
            orders = em.createNamedQuery("SuspendedOrdersEntity.getSuspendedOrders", SuspendedOrdersEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }
        for(int i = 0; i < orders.size(); i++){
            em.refresh(orders.get(i).getOrder());
        }
        return new ArrayList<>(orders);
    }
}