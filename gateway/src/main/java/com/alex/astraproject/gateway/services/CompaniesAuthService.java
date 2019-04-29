package com.alex.astraproject.gateway.services;

import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import reactor.core.publisher.Mono;

public interface CompaniesAuthService {

    Mono<JwtCompanyResponse> createToken(CompanyLoginDto dto);

    Mono<Company> verifyToken(VerifyCompanyTokenDto dto);

}
