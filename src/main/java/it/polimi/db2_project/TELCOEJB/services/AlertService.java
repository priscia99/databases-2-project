package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.AlertEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.InvalidCredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.NonUniqueResultException;

import java.sql.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class AlertService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public AlertService(){
    }

    public AlertEntity createNewAlert(UserEntity user, float amount, Timestamp timestamp) {

        AlertEntity newAlert = new AlertEntity(amount,timestamp,user.getEmail(),user);
        em.persist(newAlert);
        em.flush();
        return newAlert;
    }

    public AlertEntity findAlertById(int id){
        return em.find(AlertEntity.class,id);
    }


}
