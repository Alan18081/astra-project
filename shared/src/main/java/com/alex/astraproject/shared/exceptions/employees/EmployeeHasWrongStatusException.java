package com.alex.astraproject.shared.exceptions.employees;

public class EmployeeHasWrongStatusException extends RuntimeException {
    public EmployeeHasWrongStatusException(String message) {
        super(message);
    }
}
