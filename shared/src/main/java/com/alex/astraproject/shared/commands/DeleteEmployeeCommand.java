package com.alex.astraproject.shared.commands;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteEmployeeCommand {
    private UUID employeeId;

}
