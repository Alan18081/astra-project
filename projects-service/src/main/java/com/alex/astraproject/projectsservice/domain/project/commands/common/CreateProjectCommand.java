package com.alex.astraproject.projectsservice.domain.project.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectCommand {

    @NotNull
    private String name;

    @NotNull
    private String companyId;

}
