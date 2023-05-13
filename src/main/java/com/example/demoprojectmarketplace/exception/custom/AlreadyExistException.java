package com.example.demoprojectmarketplace.exception.custom;

public class AlreadyExistException  extends RuntimeException{

    public AlreadyExistException(String message) {
        super(message);
    }
}
