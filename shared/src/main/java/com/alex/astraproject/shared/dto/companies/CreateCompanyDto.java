package com.alex.astraproject.shared.dto.companies;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCompanyDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Email
    private String email;
}
