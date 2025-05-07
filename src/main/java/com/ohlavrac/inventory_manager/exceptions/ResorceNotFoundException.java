package com.ohlavrac.inventory_manager.exceptions;

public class ResorceNotFoundException extends RuntimeException {
    public ResorceNotFoundException(String message) {
        super(message);
    }
}
