package com.alex.astraproject.shared.exceptions.projects;

import com.alex.astraproject.shared.messages.Errors;

public class ProjectAlreadyHaveRequiredPositionException extends RuntimeException {
	public ProjectAlreadyHaveRequiredPositionException() {
		super(Errors.PROJECT_ALREADY_HAVE_POSITION);
	}
}
