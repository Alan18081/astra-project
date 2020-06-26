package com.alex.astraproject.companiesservice.domain.position.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePositionCommand {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String companyId;

}
