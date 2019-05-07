package com.alex.astraproject.companiesservice.domain.company.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCompanyCommand {
    private String companyId;
}
