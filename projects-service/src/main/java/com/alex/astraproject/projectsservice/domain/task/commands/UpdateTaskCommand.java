package com.alex.astraproject.projectsservice.domain.task.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskCommand {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

}
