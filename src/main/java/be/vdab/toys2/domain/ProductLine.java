package be.vdab.toys2.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="productlines")
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "productLine")
    private Set<Product> products = new LinkedHashSet<Product>();
    @Version
    private long version;
    protected ProductLine() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    public long getVersion() {
        return version;
    }
}
