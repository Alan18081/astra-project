package com.alex.astraproject.shared.responses;

import lombok.Data;

@Data
public class JwtCompanyResponse {
    private String token;
    private long expiresIn;
}
