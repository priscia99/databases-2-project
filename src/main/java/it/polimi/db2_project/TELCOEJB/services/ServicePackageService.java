package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.PeriodEntity;
import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.*;
import it.polimi.db2_project.TELCOEJB.exceptions.NonUniqueResultException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class ServicePackageService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves all the service packages from the database
     * @return the list of entities containing all the service packages
     * @throws ServicePackageException if an error occurs while trying to execute the query
     */
    public ArrayList<ServicePackageEntity> getAllPackages() throws ServicePackageException{
        List<ServicePackageEntity> packages;

        try{
            // retrieving the list of users that match with a given username and password
            packages = em.createNamedQuery("ServicePackageEntity.getAllPackages", ServicePackageEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new ServicePackageException("An error occoured while trying to fetch all service packages.");
        }
        
        for(int i=0; i<packages.size(); i++){
            em.refresh(packages.get(i));
            for(int j=0; j<packages.get(i).getPeriods().size(); j++){
                em.refresh(packages.get(i).getPeriods().get(j));
            }
        }

        return new ArrayList<>(packages);
    }

    /**
     * Retrieves a single service package given its ID in input
     * @param packageId ID of the service package to be retrieved
     * @return the service package entity associated with the given ID
     * @throws ServicePackageException if the service package is not found
     */
    public ServicePackageEntity getPackageById(int packageId) throws ServicePackageException{
        ServicePackageEntity res = em.find(ServicePackageEntity.class, packageId);
        if(res == null){
            throw new ServicePackageException("Service package not found.");
        }
        for(int i=0; i<res.getPeriods().size(); i++){
            em.refresh(res.getPeriods().get(i));
        }
        return res;
    }

    /**
     * Persists a service package entity given in input
     * @param p the service package entity to be persisted
     */
    public void persistServicePackage(ServicePackageEntity p){
        em.persist(p);
        em.flush();
    }



}
