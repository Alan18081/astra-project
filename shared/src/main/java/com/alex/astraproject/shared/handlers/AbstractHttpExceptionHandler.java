package com.alex.astraproject.shared.handlers;

import com.alex.astraproject.shared.HttpError;
import com.alex.astraproject.shared.responses.HttpErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public abstract class AbstractHttpExceptionHandler {

	protected Mono<ResponseEntity<HttpErrorResponse>> handle(HttpStatus status, RuntimeException ex) {
		return Mono.just(
			new ResponseEntity<>(
				new HttpErrorResponse(ex.getMessage(), System.currentTimeMillis(), status.value()),
				status
			)
		);
	}

}
