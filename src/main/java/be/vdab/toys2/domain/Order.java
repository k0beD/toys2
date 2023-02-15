package be.vdab.toys2.domain;

import be.vdab.toys2.exceptions.OrderShippedException;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private long id;
    private LocalDate ordered;
    private LocalDate required;
    private LocalDate shipped;
    private String comments;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ElementCollection
    @CollectionTable(name = "orderdetails", joinColumns = @JoinColumn(name= "orderId"))
    @Column(name="orderdetails")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<OrderDetail>();

    @Enumerated(EnumType.STRING)
    private Status status;
    @Version
    private long version;

    protected Order() {}

    public long getId() {
        return id;
    }

    public LocalDate getOrdered() {
        return ordered;
    }

    public LocalDate getRequired() {
        return required;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public String getComments() {
        return comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Status getStatus() {
        return status;
    }

    public long getVersion() {
        return version;
    }

    public Set<OrderDetail> getOrderDetails () {
        return Collections.unmodifiableSet(orderDetails);
    }

    public BigDecimal getTotalValue() {
        var value = BigDecimal.valueOf(0);
        for (var orderDetail : orderDetails) {
            value = value.add(orderDetail.getValue());
        }
        return value;
    }

    public void ship() {
        if (status == Status.SHIPPED) {
            throw new OrderShippedException();
        } else {
            status = Status.SHIPPED;
            shipped = LocalDate.now();

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
