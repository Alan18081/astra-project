package com.alex.astraproject.shared.exceptions.projects;

import com.alex.astraproject.shared.messages.Errors;

public class ProjectDoesNotHaveRequiredPositionException extends RuntimeException {
	public ProjectDoesNotHaveRequiredPositionException() {
		super(Errors.PROJECT_DOES_NOT_HAVE_REQUIRED_POSITION);
	}
}
