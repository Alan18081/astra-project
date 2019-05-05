package com.alex.astraproject.gateway.controllers.exceptionsHandlers;

import com.alex.astraproject.shared.exceptions.common.InvalidPasswordException;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
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

	@ExceptionHandler({InvalidPasswordException.class})
	public Mono<ResponseEntity<HttpErrorResponse>> handle(InvalidPasswordException ex) {
		return super.handle(HttpStatus.BAD_REQUEST, ex);
	}
}
