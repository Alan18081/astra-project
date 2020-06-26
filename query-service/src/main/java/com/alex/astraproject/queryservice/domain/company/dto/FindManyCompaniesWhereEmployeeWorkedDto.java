package com.alex.astraproject.queryservice.domain.company.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindManyCompaniesWhereEmployeeWorkedDto extends PaginationDto {

	@NotNull
	private String employeeId;

}
