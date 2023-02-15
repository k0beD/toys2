package be.vdab.toys2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    private long id;
    private String name;
    @Version
    private long version;

    protected Country() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }
}
