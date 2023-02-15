package be.vdab.toys2.domain;

import be.vdab.toys2.exceptions.OnvoldoendeVoorraadException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String scale;
    private String description;
    private int inStock;
    private int inOrder;
    private BigDecimal price;

 @ElementCollection
@CollectionTable(name = "orderdetails")
   private Set<OrderDetail> orderDetails = new LinkedHashSet<OrderDetail>();

    @Version
    private long version;

    protected Product() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public int getInStock() {
        return inStock;
    }

    public int getInOrder() {
        return inOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }



    public Set<OrderDetail> getOrderDetails() {
        return Collections.unmodifiableSet(orderDetails);
   }

    public long getVersion() {
        return version;
    }

    public void ship(int aantal) {
        if (inStock - aantal < 0) {
            throw new OnvoldoendeVoorraadException(name);
        } else {
            inStock -= aantal;
            inOrder -= aantal;
        }
    }
}
