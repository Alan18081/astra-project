package com.alex.astraproject.shared.commands;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateEmployeeCommand {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
