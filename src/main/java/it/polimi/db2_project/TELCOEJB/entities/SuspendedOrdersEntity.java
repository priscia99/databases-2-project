package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "suspended_orders")
@NamedQueries({
        @NamedQuery(name = "SuspendedOrdersEntity.getSuspendedOrders", query = "SELECT o FROM SuspendedOrdersEntity o"),
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
