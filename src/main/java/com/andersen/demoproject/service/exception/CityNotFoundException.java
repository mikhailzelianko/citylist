package com.andersen.demoproject.service.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Integer id) {
        super(String.format("City with Id %d not found", id));
    }
}
