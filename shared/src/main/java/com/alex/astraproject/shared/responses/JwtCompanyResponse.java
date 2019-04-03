package com.alex.astraproject.shared.responses;

import com.alex.astraproject.shared.entities.Company;
import lombok.Data;

@Data
public class JwtCompanyResponse {
    private String token;
    private long expiresIn;
    private Company company;

    public JwtCompanyResponse(String token, long expiresIn, Company company) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.company = company;
    }
}
