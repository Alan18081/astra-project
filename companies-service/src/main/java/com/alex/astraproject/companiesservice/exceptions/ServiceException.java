package com.alex.astraproject.companiesservice.exceptions;

public abstract class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
