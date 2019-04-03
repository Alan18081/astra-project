package com.alex.astraproject.shared.dto.employees;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FindManyEmployeesDto extends PaginationDto {

    @NotNull
    private long companyId;

}
