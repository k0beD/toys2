package be.vdab.toys2.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private long id;
    private String name;
    @Embedded
    private Adres adres;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private Country country;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    @Version
    private long version;

    protected Customer() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Adres getAdres() {
        return adres;
    }

    public Country getCountry() {
        return country;
    }

    public long getVersion() {
        return version;
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }
}
