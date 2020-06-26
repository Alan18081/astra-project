package com.alex.astraproject.projectsservice.domain.project.commands.participants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeCommand {

   private String projectId;

   @NotNull
   private String employeeId;

   @NotNull
   private String positionId;

}
