package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.PeriodEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.PeriodException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PeriodService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    // Get a period entity object given its ID
    public PeriodEntity getPeriodById(int periodId) throws PeriodException{
        PeriodEntity res = em.find(PeriodEntity.class, periodId);
        if(res == null){
            throw new PeriodException("Validity period not found.");
        }
        return res;
    }
}
