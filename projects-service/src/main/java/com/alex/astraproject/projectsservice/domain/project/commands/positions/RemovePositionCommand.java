package com.alex.astraproject.projectsservice.domain.project.commands.positions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemovePositionCommand {

   private String projectId;

   @NotNull
   private String positionId;

}
