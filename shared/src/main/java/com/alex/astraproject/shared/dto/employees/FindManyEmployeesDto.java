package com.alex.astraproject.shared.dto.employees;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FindManyEmployeesDto {

    @NotNull
    private Long companyId;

}
