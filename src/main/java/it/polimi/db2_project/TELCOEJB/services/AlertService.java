package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.AlertEntity;
import it.polimi.db2_project.TELCOEJB.entities.InsolventUsersEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.AdminViewsException;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.InvalidCredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.NonUniqueResultException;

import java.sql.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AlertService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public AlertService(){
    }

    /**
     * Retrieves the list of alerts
     * @return the list of entities containing the alerts
     * @throws AdminViewsException if an error occurs while trying to execute the query
     */
    public List<AlertEntity> getAlerts() throws AdminViewsException {
        List<AlertEntity> alerts;

        try{
            // retrieving the list of users that match with a given username and password
            alerts = em.createNamedQuery("AlertEntity.getAlerts", AlertEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new AdminViewsException("An error occoured while trying to fetch sales report.");
        }

        return new ArrayList<>(alerts);
    }

    public void persistAlert(AlertEntity alert){
        em.persist(alert);
        em.flush();
    }

}
