package com.alex.astraproject.companiesservice.domain.employee.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmployeePositionCommand {

	private String employeeId;

	@NotNull
	private String positionId;

}
