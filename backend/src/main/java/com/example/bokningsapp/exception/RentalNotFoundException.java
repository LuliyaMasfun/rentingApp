package com.example.bokningsapp.exception;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(String message) {
        super(message);
    }
}
