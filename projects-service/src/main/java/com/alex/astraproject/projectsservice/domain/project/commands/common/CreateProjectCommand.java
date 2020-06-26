package com.alex.astraproject.projectsservice.domain.project.commands.common;

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

    private String description;

    @NotNull
    private String companyId;

}
