package com.alex.astraproject.queryservice.domain.employee.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FindManyEmployeeThatWorksWithProvidedEmployee extends PaginationDto {

	private String employeeId;

}
