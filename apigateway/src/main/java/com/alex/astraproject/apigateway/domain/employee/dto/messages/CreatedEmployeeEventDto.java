package com.alex.astraproject.apigateway.domain.employee.dto.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CreatedEmployeeEventDto {

    @NotNull
    private String id;

    @NotNull
    private UUID employeeId;

    @NotNull
    private Object data;

    @NotNull
    private int revision;

}
