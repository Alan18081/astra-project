package com.alex.astraproject.companiesservice.domain.company.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GetEventsDto extends PaginationDto {

	private String entityId;

}
