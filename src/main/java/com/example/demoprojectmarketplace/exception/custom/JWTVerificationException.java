package com.example.demoprojectmarketplace.exception.custom;

public class JWTVerificationException extends RuntimeException{

    public JWTVerificationException(String message) {
        super(message);
    }
}
