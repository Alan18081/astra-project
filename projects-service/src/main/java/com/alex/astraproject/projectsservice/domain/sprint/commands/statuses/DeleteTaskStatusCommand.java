package com.alex.astraproject.projectsservice.domain.sprint.commands.statuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTaskStatusCommand {

	private String sprintId;

	@NotEmpty
	private String statusName;
}
