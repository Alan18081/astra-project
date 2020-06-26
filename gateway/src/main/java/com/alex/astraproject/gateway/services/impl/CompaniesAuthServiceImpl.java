package com.alex.astraproject.gateway.services.impl;

import com.alex.astraproject.gateway.clients.CompanyClient;
import com.alex.astraproject.gateway.exceptions.CompanyInvalidPasswordException;
import com.alex.astraproject.gateway.exceptions.JwtTokenExtractException;
import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.gateway.services.JwtService;
import com.alex.astraproject.gateway.services.PasswordService;
import com.alex.astraproject.gateway.shared.AppProperties;
import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.exceptions.companies.InvalidCompanyAuthToken;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.JwtResponse;
import com.alex.astraproject.shared.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CompaniesAuthServiceImpl implements AuthService<Company> {

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<JwtResponse<Company>> createToken(LoginDto dto) {
        return companyClient.findCompanyByEmail(dto.getEmail())
          .switchIfEmpty(Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_EMAIL)))
          .flatMap(company -> {
            if(!passwordService.comparePassword(dto.getPassword(), company.getPassword())) {
              return Mono.error(new CompanyInvalidPasswordException());
            }
            return Mono.just(company);
          })
          .flatMap(company -> {
              String token = jwtService.generateToken(company);
              return Mono.just(new JwtResponse<>(token, SecurityConstants.EXPIRATION_TIME, company));
          });
    }

    @Override
    public Mono<Company> verifyToken(String token) {
        if(token == null) {
            return Mono.error(new JwtTokenExtractException(Errors.INVALID_COMPANY_AUTH_TOKEN));
        }
        String email = jwtService.getUsernameFromToken(token);

        if (email == null) {
           throw new InvalidCompanyAuthToken();
        }

        return companyClient.findCompanyByEmail(email);
    }
}
