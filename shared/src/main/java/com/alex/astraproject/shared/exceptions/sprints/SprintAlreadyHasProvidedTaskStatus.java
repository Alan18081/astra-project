package com.alex.astraproject.shared.exceptions.sprints;

import com.alex.astraproject.shared.messages.Errors;

public class SprintAlreadyHasProvidedTaskStatus extends RuntimeException {
	public SprintAlreadyHasProvidedTaskStatus() {
		super(Errors.SPRINT_ALREADY_HAS_STATUS);
	}
}
