package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.OptionalProductEntity;
import it.polimi.db2_project.TELCOEJB.entities.ServiceEntity;
import it.polimi.db2_project.TELCOEJB.entities.ServicePackageEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.*;

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
public class ServiceService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public ArrayList<ServiceEntity> getAllServices() throws ServiceException {
        List<ServiceEntity> services;

        try{
            // retrieving the list of users that match with a given username and password
            services = em.createNamedQuery("ServiceEntity.getAllServices", ServiceEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new ServiceException("An error occoured while trying to fetch all services.");
        }

        return new ArrayList<>(services);
    }

    private ServiceEntity getService(String serviceId) throws OptionalProductException {
        ServiceEntity serviceEntity;
        try{
            serviceEntity = em.find(ServiceEntity.class,Integer.parseInt(serviceId));
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new OptionalProductException("An error occoured while trying to fetch service by id");
        }
        return serviceEntity;
    }

    public List<ServiceEntity> getListServices(List<String> servicesList) throws ServiceException{
        List<ServiceEntity> serviceEntities = new ArrayList<>();
        for(int i = 0; i < servicesList.size(); i++){
            try {
                serviceEntities.add(getService(servicesList.get(i)));
            } catch (PersistenceException | OptionalProductException e){
                e.printStackTrace();
                throw new ServiceException("An error occoured while trying to fetch service list by id");
            }
        }
        return new ArrayList<>(serviceEntities);
    }

}
