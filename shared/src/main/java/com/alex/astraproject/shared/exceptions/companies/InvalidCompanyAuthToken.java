package com.alex.astraproject.shared.exceptions.companies;

import com.alex.astraproject.shared.messages.Errors;

public class InvalidCompanyAuthToken extends RuntimeException {
    public InvalidCompanyAuthToken() {
        super(Errors.INVALID_COMPANY_AUTH_TOKEN);
    }
}
