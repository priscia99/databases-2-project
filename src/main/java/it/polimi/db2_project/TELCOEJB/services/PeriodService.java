package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.PeriodEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.PeriodException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Stateless
public class PeriodService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves a single service package given its ID in input
     * @param periodId ID of the period to be retrieved
     * @return the period entity associated with the given ID
     * @throws PeriodException if the validity period is not found
     */
    public PeriodEntity getPeriodById(int periodId) throws PeriodException{
        PeriodEntity res = em.find(PeriodEntity.class, periodId);
        if(res == null){
            throw new PeriodException("Validity period not found.");
        }
        return res;
    }

    /**
     * Persists a validity period entity given in input
     * @param period the period entity to be persisted
     */
    public void persistPeriod(PeriodEntity period){
        em.persist(period);
        em.flush();
    }

    /**
     * Persists a list of validity periods given in input
     * @param validityPeriods the list of validity periods to be persisted
     */
    public void persistPeriods(ArrayList<PeriodEntity> validityPeriods){
        for(PeriodEntity p : validityPeriods){
            this.persistPeriod(p);
        }
    }
}
