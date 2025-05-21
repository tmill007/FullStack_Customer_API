package com.amigoscode.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class duplicateResourceException extends RuntimeException{
    public duplicateResourceException(String message) {
        super(message);
    }
}
