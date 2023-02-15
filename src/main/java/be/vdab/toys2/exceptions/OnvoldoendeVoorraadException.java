package be.vdab.toys2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OnvoldoendeVoorraadException extends RuntimeException{
    public OnvoldoendeVoorraadException(String name) {
        super("onvoldoende voorraad! Product: " + name);
    }
}
