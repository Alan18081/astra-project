package com.alex.astraproject.authservice.services;

import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;

public interface CompaniesAuthService {

    JwtCompanyResponse createToken(CompanyLoginDto dto);

}
