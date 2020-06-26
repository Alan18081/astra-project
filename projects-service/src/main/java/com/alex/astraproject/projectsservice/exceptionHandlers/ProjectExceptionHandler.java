package com.alex.astraproject.projectsservice.exceptionHandlers;

import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeHasWrongStatusException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveDesiredStatusException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveRequiredPositionException;
import com.alex.astraproject.shared.exceptions.projects.ProjectDoesNotHaveRequiredEmployeeException;
import com.alex.astraproject.shared.exceptions.projects.ProjectDoesNotHaveRequiredPositionException;
import com.alex.astraproject.shared.handlers.AbstractHttpExceptionHandler;
import com.alex.astraproject.shared.responses.HttpErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ProjectExceptionHandler extends AbstractHttpExceptionHandler {

	@ExceptionHandler({
		ProjectAlreadyHaveDesiredStatusException.class,
		ProjectAlreadyHaveRequiredPositionException.class,
		ProjectDoesNotHaveRequiredEmployeeException.class,
		ProjectDoesNotHaveRequiredPositionException.class
	})
	public Mono<ResponseEntity<HttpErrorResponse>> handle(RuntimeException ex) {
		return super.handle(HttpStatus.BAD_REQUEST, ex);
	}
}
