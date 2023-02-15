package be.vdab.toys2.domain;

import jakarta.persistence.*;

@Embeddable
@Access(AccessType.FIELD)
public class Adres {
    private String streetAndNumber;
    private String city;
    private String state;
    private String postalCode;

    protected Adres(){}

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

}
