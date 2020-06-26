package com.alex.astraproject.queryservice.domain.project.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindManyProjectsWhereTwoEmployeesWorksDto extends PaginationDto {

	@NotNull
	private String companyId;

	@Size(min = 2, max = 2)
	private List<String> employeeIds;
}
