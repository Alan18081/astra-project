package com.alex.astraproject.shared.dto.positions;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class FindManyPositionsDto extends PaginationDto {
    @PositiveOrZero
    private double salaryFrom;

    @PositiveOrZero
    private double salaryTo;
    private String name;

    @NotNull
    private long companyId;
}
