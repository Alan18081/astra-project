package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.apigateway.entities.Company;
import lombok.Data;

@Data
public class CompanyLoginResponse {
    private String token;
    private long expiresIn;
    private Company company;

    public CompanyLoginResponse(String token, long expiresIn, Company company) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.company = company;
    }
}
