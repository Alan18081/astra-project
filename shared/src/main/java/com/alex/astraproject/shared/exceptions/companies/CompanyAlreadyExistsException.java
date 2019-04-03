package com.alex.astraproject.shared.exceptions.companies;

import com.alex.astraproject.shared.messages.Errors;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException() {
        super(Errors.COMPANY_ALREADY_EXISTS);
    }
}
