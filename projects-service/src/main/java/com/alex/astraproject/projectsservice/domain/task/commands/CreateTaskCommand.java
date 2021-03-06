package com.alex.astraproject.projectsservice.domain.task.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskCommand {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String sprintId;

}
