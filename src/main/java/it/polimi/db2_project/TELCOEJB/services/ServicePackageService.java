package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.InvalidCredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.NonUniqueResultException;
import it.polimi.db2_project.TELCOEJB.exceptions.ServicePackageException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ServicePackageService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public ArrayList<ServicePackageEntity> getAllPackages() throws ServicePackageException{
        List<ServicePackageEntity> packages;

        try{
            // retrieving the list of users that match with a given username and password
            packages = em.createNamedQuery("ServicePackageEntity.getAllPackages", ServicePackageEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            throw new ServicePackageException("An error occoured while trying to fetch all service packages.");
        }

        return new ArrayList<>(packages);
    }
}
