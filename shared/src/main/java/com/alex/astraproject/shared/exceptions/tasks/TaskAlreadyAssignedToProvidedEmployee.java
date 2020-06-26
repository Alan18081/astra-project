package com.alex.astraproject.shared.exceptions.tasks;

import com.alex.astraproject.shared.messages.Errors;

public class TaskAlreadyAssignedToProvidedEmployee extends RuntimeException {
	public TaskAlreadyAssignedToProvidedEmployee() {
		super(Errors.TASK_ALREADY_ASSIGNED_TO_PROVIDED_EMPLOYEE);
	}
}
