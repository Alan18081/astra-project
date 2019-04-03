package com.alex.astraproject.shared.dto.companies;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerifyCompanyTokenDto {

    @NotNull
    private String token;

    public VerifyCompanyTokenDto(@NotNull String token) {
        this.token = token;
    }
}
