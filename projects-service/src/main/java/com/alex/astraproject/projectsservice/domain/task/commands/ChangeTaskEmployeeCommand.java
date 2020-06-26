package com.alex.astraproject.projectsservice.domain.task.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTaskEmployeeCommand {

	private String id;

	@NotNull
	private String employeeId;

}
