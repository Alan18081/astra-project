package com.alex.astraproject.projectsservice.domain.project.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeParticipantPositionCommand {

   @NotNull
   private String projectId;

   @NotNull
   private String employeeId;

}
