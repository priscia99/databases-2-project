package it.polimi.db2_project.TELCOEJB.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bestseller_optionalproduct")
@NamedQueries({
        @NamedQuery(name = "BestsellerOptionalProductEntity.getBestsellerProduct", query = "SELECT o FROM BestsellerOptionalProductEntity o"),
})
public class BestsellerOptionalProductEntity {

    @Id
    @Column(name = "id", nullable = false)
    private int ID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productID")
    private OptionalProductEntity optionalProduct;

    @Column(name = "sales", nullable = false)
    private int sales;

    public BestsellerOptionalProductEntity() {
    }

    public int getID() {
        return ID;
    }

    public int getSales() {
        return sales;
    }

    public OptionalProductEntity getOptionalProduct() {
        return optionalProduct;
    }
}
