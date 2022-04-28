package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
import it.polimi.db2_project.TELCOEJB.entities.UserEntity;
import it.polimi.db2_project.TELCOEJB.exceptions.CredentialsException;
import it.polimi.db2_project.TELCOEJB.exceptions.OrderException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public OrderService() {
    }
    public OrderEntity findOrderById(int orderId) {
        return em.createNamedQuery("OrderEntity.findById", OrderEntity.class)
                .setParameter("orderId",orderId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    public void persistOrder(OrderEntity order) throws OrderException {
        if (findOrderById(order.getOrderId()) != null) {
            throw new OrderException("Order id already in use!");
        }
        em.persist(order);
    }
}
