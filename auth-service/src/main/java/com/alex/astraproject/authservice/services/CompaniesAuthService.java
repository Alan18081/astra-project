package com.alex.astraproject.authservice.services;

import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;

public interface CompaniesAuthService {

    JwtCompanyResponse createToken(Company dto);

    Company verifyToken(VerifyCompanyTokenDto dto);

}
