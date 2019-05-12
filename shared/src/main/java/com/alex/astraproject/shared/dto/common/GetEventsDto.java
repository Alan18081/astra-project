package com.alex.astraproject.shared.dto.common;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Data
@ToString
public class GetEventsDto extends PaginationDto {

	private String entityId;

	private Long revisionFrom;

	private Long revisionTo;

	private Long timestampFrom;

	private Long timestampTo;

	public GetEventsDto() {
		super(1, 100);
	}
}
