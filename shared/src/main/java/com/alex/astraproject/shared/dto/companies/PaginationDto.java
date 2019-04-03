package com.alex.astraproject.shared.dto.companies;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PaginationDto {

    @NotNull
    @Positive
    private int page;

    @NotNull
    @Positive
    private int limit;
}
