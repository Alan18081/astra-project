package com.alex.astraproject.shared.dto.companies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindCompanyByIdDto {

	private UUID id;

}
