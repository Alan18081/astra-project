package com.alex.astraproject.companiesservice.domain.company.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventsByTimestampDto extends PaginationDto {

	private String id;

	private long timestampFrom;

	private long timestampTo;

}
