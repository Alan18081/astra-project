package com.alex.astraproject.projectsservice.exceptionHandlers;

import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveDesiredStatusException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveRequiredPositionException;
import com.alex.astraproject.shared.handlers.AbstractHttpExceptionHandler;
import com.alex.astraproject.shared.responses.HttpErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class CommonExceptionHandler extends AbstractHttpExceptionHandler {

	@ExceptionHandler({NotFoundException.class})
	public Mono<ResponseEntity<HttpErrorResponse>> handle(NotFoundException ex) {
		return super.handle(HttpStatus.NOT_FOUND, ex);
	}

	@ExceptionHandler({
		ProjectAlreadyHaveDesiredStatusException.class,
		ProjectAlreadyHaveRequiredPositionException.class
	})
	public Mono<ResponseEntity<HttpErrorResponse>> handle(EmployeeAlreadyExistsException ex) {
		return super.handle(HttpStatus.BAD_REQUEST, ex);
	}
}
