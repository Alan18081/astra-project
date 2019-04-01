package com.alex.astraproject.companiesservice.dto.companies;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCompanyDto {

    private String name;

    private String corporateEmail;

}
