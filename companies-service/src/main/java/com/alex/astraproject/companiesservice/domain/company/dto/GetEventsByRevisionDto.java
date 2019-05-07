package com.alex.astraproject.companiesservice.domain.company.dto;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import com.alex.astraproject.shared.validators.annotations.IsNotBothNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class GetEventsByRevisionDto extends PaginationDto {

	private String id;

	private Long revisionFrom;

	private Long revisionTo;

	public GetEventsByRevisionDto() {
		super(1, 20);
	}
}
