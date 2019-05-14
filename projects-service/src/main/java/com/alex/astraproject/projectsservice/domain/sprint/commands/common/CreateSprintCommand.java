package com.alex.astraproject.projectsservice.domain.sprint.commands.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSprintCommand {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String companyId;

}
