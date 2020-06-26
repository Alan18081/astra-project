package com.alex.astraproject.shared.exceptions.tasks;

import com.alex.astraproject.shared.messages.Errors;

public class TaskAlreadyHasProvidedStatus extends RuntimeException {
	public TaskAlreadyHasProvidedStatus() {
		super(Errors.TASK_ALREADY_HAS_PROVIDED_STATUS);
	}
}
