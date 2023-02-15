package be.vdab.toys2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrderDoorAndereGebruikerGewijzigdException extends RuntimeException{
    public OrderDoorAndereGebruikerGewijzigdException() {
        super("Order werd door andere gebruiker gewijzgd.");
    }
}
