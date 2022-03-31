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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
            e.printStackTrace();
            throw new ServicePackageException("An error occoured while trying to fetch all service packages.");
        }

        return new ArrayList<>(packages);
    }

    public List<ServicePackageEntity> getPackagesById(String chosenPackageId) throws ServicePackageException {
        List<ServicePackageEntity> packages;

        try{
            // retrieving the list of users that match with a given username and password
            packages = em.createNamedQuery("ServicePackageEntity.getPackagesById", ServicePackageEntity.class)
                    .setParameter("packageId", Integer.parseInt(chosenPackageId))
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new ServicePackageException("An error occoured while trying to fetch packages by id.");
        }

        return new ArrayList<>(packages);
    }

    public HashMap<Integer,ArrayList<ServicePackageEntity>> getAllPackagesToMap() throws ServicePackageException{
        HashMap<Integer,ArrayList<ServicePackageEntity>> packages;
        packages = new HashMap<>();

        try{
            // retrieving the list of users that match with a given username and password
            List<ServicePackageEntity> packagesList;
            packagesList = em.createNamedQuery("ServicePackageEntity.getAllPackages", ServicePackageEntity.class)
                    .getResultList();
            //converting it into map
            for(int i = 0; i< packagesList.size(); i++){
                if(!(packages.containsKey(packagesList.get(i).getPackageId()))){
                    ArrayList<ServicePackageEntity> newPackageList = new ArrayList<>();
                    newPackageList.add(packagesList.get(i));
                    packages.put(packagesList.get(i).getPackageId(),newPackageList);
                }else{
                    ArrayList<ServicePackageEntity> newPackageList = packages.get(packagesList.get(i).getPackageId());
                    newPackageList.add(packagesList.get(i));
                    packages.put(packagesList.get(i).getPackageId(),newPackageList);
                }
            }
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new ServicePackageException("An error occoured while trying to fetch all service packages.");
        }

        return packages;
    }

    public ServicePackageEntity getPackagesByIdAndValidityPeriod(String chosenPackageId, String chosenValidityPeriod) throws ServicePackageException {
        List<ServicePackageEntity> packages;

        try{
            // retrieving the list of users that match with a given username and password
            packages = em.createNamedQuery("ServicePackageEntity.getPackagesByIdAndValidityPeriod", ServicePackageEntity.class)
                    .setParameter("packageId", Integer.parseInt(chosenPackageId))
                    .setParameter("validityPeriod",Integer.parseInt(chosenValidityPeriod))
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new ServicePackageException("An error occoured while trying to fetch packages by id.");
        }
        if(packages.size() > 1)
            throw new ServicePackageException("Multiple instances found while trying to fetch packages by id and validity period");
        return packages.get(0);
    }
}
