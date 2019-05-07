package com.alex.astraproject.projectsservice.domain.project.commands.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopProjectCommand {

   @NotNull
   private String id;

}
