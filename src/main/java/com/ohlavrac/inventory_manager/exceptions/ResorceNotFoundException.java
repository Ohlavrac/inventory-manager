package com.ohlavrac.inventory_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResorceNotFoundException extends RuntimeException {
    public ResorceNotFoundException(String message) {
        super(message);
    }
}
