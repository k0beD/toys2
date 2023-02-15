package be.vdab.toys2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrderShippedException extends RuntimeException{
    public OrderShippedException() {
        super("Order is shipped.");
    }
}