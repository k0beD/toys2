package be.vdab.toys2.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class OrderDetail {

    private int ordered;
    private BigDecimal priceEach;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;


    public BigDecimal getValue() {
        return priceEach.multiply(BigDecimal.valueOf(ordered));
    }

    public int getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail that)) return false;
        return product.equals(that.product) && order.equals(that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }
}