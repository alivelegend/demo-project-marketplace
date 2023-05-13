package com.example.demoprojectmarketplace.exception.custom;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
