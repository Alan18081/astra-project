package com.alex.astraproject.gateway.exceptions;

import com.alex.astraproject.shared.messages.Errors;

public class CompanyInvalidPasswordException extends RuntimeException {
	public CompanyInvalidPasswordException() {
		super(Errors.INVALID_COMPANY_PASSWORD);
	}
}
