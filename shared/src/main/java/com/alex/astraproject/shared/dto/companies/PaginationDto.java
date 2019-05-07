package com.alex.astraproject.shared.dto.companies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {

    @NotNull
    @Positive
    private int page;

    @NotNull
    @Positive
    private int limit;
}
