package com.example.demoprojectlive.service;


public class OrganizationAlreadyExistsException extends Exception {
    public OrganizationAlreadyExistsException(String message) {
        super(message);
    }
}
