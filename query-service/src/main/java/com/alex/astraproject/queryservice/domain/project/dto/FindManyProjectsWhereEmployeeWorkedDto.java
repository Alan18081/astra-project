package com.alex.astraproject.queryservice.domain.project.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindManyProjectsWhereEmployeeWorkedDto extends PaginationDto {

	@NotNull
	private String companyId;

	private String employeeId;

	private Date startDate;

	private Date endDate;

}
