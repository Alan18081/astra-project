package com.alex.astraproject.projectsservice.domain.sprint.commands.statuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskStatusCommand {

	private String sprintId;

	@NotBlank
	private String name;

	@NotEmpty
	private List<String> userIds;
}
