package com.alex.astraproject.queryservice.domain.sprint.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindManySprintsDto extends PaginationDto {

	@NotNull
	private String projectId;

	private Date createdAtFrom;

	private Date createdAtTo;

	private Date completedAtFrom;

	private Date completedAtTo;

	private boolean completed;

}
