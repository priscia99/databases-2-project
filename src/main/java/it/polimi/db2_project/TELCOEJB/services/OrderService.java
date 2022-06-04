package it.polimi.db2_project.TELCOEJB.services;

import it.polimi.db2_project.TELCOEJB.entities.OrderEntity;
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

    /**
     * Retrieves an order that matches with the ID given in input
     * @param orderId The ID of the order to be retrieved
     * @return the order entity that is associated with the given ID
     */
    public OrderEntity findOrderById(int orderId) {
        return em.find(OrderEntity.class,orderId);
    }

    /**
     * Persist an order entity given in input
     * @param order the order entity to be persisted
     * @throws OrderException if there is an error while trying to persist the order
     */
    public void persistOrder(OrderEntity order) throws OrderException {
        em.persist(order);
        em.flush();
    }

    /**
     * Updates the status of an order to the database
     * @param order the order entity that needs to be updated
     * @throws OrderException if the order is not found
     */
    public void updateOrderOnState(OrderEntity order) throws OrderException {
        OrderEntity oldOrder = findOrderById(order.getOrderId());
        if (oldOrder == null) {
            throw new OrderException("Order not found");
        }
        oldOrder.setOrderState(order.getOrderState());
        em.persist(oldOrder);
        em.flush();
    }
}
