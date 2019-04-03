package com.alex.astraproject.shared.dto.positions;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePositionDto {
    private String name;
    private double salary;
}
