package com.alex.astraproject.projectsservice.domain.sprint.commands.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteSprintCommand {

   @NotNull
   private String id;

}
