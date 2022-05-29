package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.OptionalProductEntity;
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
public class OptionalProductService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    /**
     * Retrieves all the optional products from the database
     * @return the list of entities containing all the optional products
     * @throws OptionalProductException if an error occurs while trying to execute the query
     */
    public ArrayList<OptionalProductEntity> getAllOptionalProducts() throws OptionalProductException {
        List<OptionalProductEntity> optionalProducts;

        try{
            // retrieving the list of users that match with a given username and password
            optionalProducts = em.createNamedQuery("OptionalProductsEntity.getAllOptionalProducts", OptionalProductEntity.class)
                    .getResultList();
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new OptionalProductException("An error occoured while trying to fetch all optional products.");
        }

        return new ArrayList<>(optionalProducts);
    }

    /**
     * Retrieves a single optional product given its ID in input
     * @param productId ID of the optional product to be retrieved
     * @return the optional product entity associated with the given ID
     * @throws OptionalProductException if the service package is not found
     */
    private OptionalProductEntity getOptionalProduct(String productId) throws OptionalProductException {
        OptionalProductEntity optionalProduct;
        try{
            optionalProduct = em.find(OptionalProductEntity.class,Integer.parseInt(productId));
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new OptionalProductException("An error occoured while trying to fetch optional product by id");
        }
        return optionalProduct;
    }

    /**
     * Retrieves a list of optional products that is obtained by giving a list of IDs in input
     * @param productIdList the list of IDs of the optional products that need to be retrieved
     * @return a list of optional products
     * @throws OptionalProductException if an error occurs while trying to execute the query
     */
    public List<OptionalProductEntity> getListOptionalProducts(List<String> productIdList) throws OptionalProductException{
        List<OptionalProductEntity> optionalProductEntities = new ArrayList<>();
        for(int i = 0; i < productIdList.size(); i++){
            try {
                optionalProductEntities.add(getOptionalProduct(productIdList.get(i)));
            } catch (PersistenceException e){
                e.printStackTrace();
                throw new OptionalProductException("An error occoured while trying to fetch optional product list by id");
            }
        }
        return new ArrayList<>(optionalProductEntities);
    }

    /**
     * Persists an optional product entity given in input
     * @param optionalProductEntity the optional product entity to be persisted
     */
    public void persistOptionalProduct(OptionalProductEntity optionalProductEntity) {
        em.persist(optionalProductEntity);
        em.flush();
    }
}
