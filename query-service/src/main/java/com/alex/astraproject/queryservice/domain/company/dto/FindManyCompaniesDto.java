package com.alex.astraproject.queryservice.domain.company.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindManyCompaniesDto extends PaginationDto {

	private Date createdAtFrom;

	private Date createdAtTo;

}
