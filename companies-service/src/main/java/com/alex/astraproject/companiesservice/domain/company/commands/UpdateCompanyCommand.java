package com.alex.astraproject.companiesservice.domain.company.commands;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Data
public class UpdateCompanyCommand {

    private UUID companyId;

    private String name;

    private String email;

    @PositiveOrZero
    private long numberOfEmployees;
}
