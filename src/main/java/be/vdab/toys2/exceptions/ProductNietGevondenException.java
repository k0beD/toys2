package be.vdab.toys2.exceptions;

public class ProductNietGevondenException extends RuntimeException{
    public ProductNietGevondenException (long id) {
        super("Product niet gevonden! ID: " + id);
    }
}
