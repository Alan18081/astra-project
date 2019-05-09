package com.alex.astraproject.shared.exceptions.projects;

import com.alex.astraproject.shared.messages.Errors;

public class ProjectDoesNotHaveRequiredEmployeeException extends RuntimeException {
	public ProjectDoesNotHaveRequiredEmployeeException() {
		super(Errors.PROJECT_DOES_NOT_HAVE_REQUIRED_EMPLOYEE);
	}
}
