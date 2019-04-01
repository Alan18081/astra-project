package com.alex.astraproject.companiesservice.dto.employees;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FindManyEmployeesDto {

    @NotNull
    private Long companyId;

}
