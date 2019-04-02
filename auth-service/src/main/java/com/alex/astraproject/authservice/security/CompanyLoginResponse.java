package com.alex.astraproject.authservice.security;

import com.alex.astraproject.authservice.entities.Company;
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
