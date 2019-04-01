package com.alex.astraproject.apigateway.dto.employees;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateEmployeeDto {

    private String firstName;

    private String lastName;

}
