package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;

@Entity
@Table(name = "suspended_orders")
@NamedQueries({
        @NamedQuery(name = "SuspendedOrdersEntity.getSuspendedOrders", query = "SELECT DISTINCT o FROM SuspendedOrdersEntity o"),
})
public class SuspendedOrdersEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private OrderEntity order;

    public SuspendedOrdersEntity() {
    }

    public OrderEntity getOrder() {
        return order;
    }
}
