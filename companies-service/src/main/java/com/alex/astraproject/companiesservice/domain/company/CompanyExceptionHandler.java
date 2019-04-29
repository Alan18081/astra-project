package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.shared.HttpError;
import com.alex.astraproject.shared.exceptions.companies.CompanyAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CompanyExceptionHandler {

	@ExceptionHandler(value = {CompanyAlreadyExistsException.class})
	public ResponseEntity<Object> handle(CompanyAlreadyExistsException ex) {
		return ResponseEntity.badRequest().body(
			new HttpError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis())
		);
	}

}
