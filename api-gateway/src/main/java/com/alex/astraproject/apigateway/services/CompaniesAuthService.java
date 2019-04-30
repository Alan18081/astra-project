package com.alex.astraproject.apigateway.services;

import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;

public interface CompaniesAuthService {

    JwtCompanyResponse createToken(CompanyLoginDto dto);

    Company verifyToken(VerifyCompanyTokenDto dto);

}
