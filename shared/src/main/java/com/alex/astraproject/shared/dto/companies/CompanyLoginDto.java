package com.alex.astraproject.shared.dto.companies;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CompanyLoginDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}
