package com.alex.astraproject.companiesservice.domain.employee.commands;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateEmployeeCommand {

    private String firstName;

    private String lastName;

    private UUID employeeId;

}
