package com.alex.astraproject.shared.dto.positions;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class CreatePositionDto {

    @NotNull
    private String name;

    @NotNull
    @PositiveOrZero
    private double salary;

    @NotNull
    private long companyId;

}
