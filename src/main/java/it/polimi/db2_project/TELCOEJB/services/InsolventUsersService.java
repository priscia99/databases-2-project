package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.InsolventUsersEntity;
import it.polimi.db2_project.TELCOEJB.entities.TotalSalesPerPackageEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class InsolventUsersService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public List<InsolventUsersEntity> getInsolventUsers() throws AdminViewsException{
        List<InsolventUsersEntity> users;

        try{
            // retrieving the list of users that match with a given username and password
            users = em.createNamedQuery("InsolventUsersEntity.getInsolventUsers", InsolventUsersEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }

        return new ArrayList<>(users);
    }
}