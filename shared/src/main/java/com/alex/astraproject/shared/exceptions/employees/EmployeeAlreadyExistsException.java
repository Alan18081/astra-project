package com.alex.astraproject.shared.exceptions.employees;

import com.alex.astraproject.shared.messages.Errors;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException() {
        super(Errors.EMPLOYEE_WITH_EMAIL_ALREADY_EXISTS);
    }
}
