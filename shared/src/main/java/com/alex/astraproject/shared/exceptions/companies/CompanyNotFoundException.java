package com.alex.astraproject.shared.exceptions.companies;

import com.alex.astraproject.shared.exceptions.common.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
