package com.alex.astraproject.companiesservice.domain.position.commands;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdatePositionCommand {

    private String positionId;

    @NotNull
    @NotEmpty
    private String name;
}
