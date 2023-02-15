package be.vdab.toys2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductDoorAndereGebruikerGewijzigdException extends RuntimeException{
    public ProductDoorAndereGebruikerGewijzigdException() {
        super("Product werd door andere gebruiker gewijzgd.");
    }
}

