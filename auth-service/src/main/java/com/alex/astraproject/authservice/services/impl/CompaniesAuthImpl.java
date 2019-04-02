package com.alex.astraproject.authservice.services.impl;

import com.alex.astraproject.authservice.services.CompaniesAuthService;
import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import org.springframework.stereotype.Service;

@Service
public class CompaniesAuthImpl implements CompaniesAuthService {
    @Override
    public JwtCompanyResponse createToken(CompanyLoginDto dto) {
        return null;
    }
}
