package com.alex.astraproject.companiesservice.domain.employee.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteEmployeeCommand {

    private String employeeId;

}
