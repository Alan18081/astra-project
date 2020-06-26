package com.alex.astraproject.shared.exceptions.projects;

public class ProjectAlreadyHaveDesiredStatusException extends RuntimeException {
	public ProjectAlreadyHaveDesiredStatusException(String message) {
		super(message);
	}
}
