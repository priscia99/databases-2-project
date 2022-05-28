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

    private OptionalProductEntity getOptionalProduct(String productId) throws OptionalProductException {
        OptionalProductEntity optionalProduct;
        try{
            // retrieving the list of users that match with a given username and password
//            optionalProduct = em.createNamedQuery("OptionalProductsEntity.getOptionalProductsById", OptionalProductEntity.class)
//                    .setParameter("productId", Integer.parseInt(productId))
//                    .getResultList().get(0);
            optionalProduct = em.find(OptionalProductEntity.class,Integer.parseInt(productId));
        }
        catch (PersistenceException e){
            e.printStackTrace();
            throw new OptionalProductException("An error occoured while trying to fetch optional product by id");
        }
        return optionalProduct;
    }

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

    public void persistOptionalProduct(OptionalProductEntity optionalProductEntity) {
        em.persist(optionalProductEntity);
        em.flush();
    }
}
