package com.alex.astraproject.authgateway.services.impl;

import com.alex.astraproject.authgateway.clients.CompaniesClient;
import com.alex.astraproject.authgateway.services.CompaniesAuthService;
import com.alex.astraproject.authgateway.shared.AppProperties;
import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.exceptions.companies.InvalidCompanyAuthToken;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import com.alex.astraproject.shared.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompaniesAuthServiceImpl implements CompaniesAuthService {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private CompaniesClient companiesClient;

    @Override
    public JwtCompanyResponse createToken(CompanyLoginDto dto) {

        Company company = companiesClient.findByEmail(dto.getEmail());

        String token = Jwts.builder()
                .setSubject(company.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, appProperties.getTokenSecret())
                .compact();

        return new JwtCompanyResponse(token, SecurityConstants.EXPIRATION_TIME, company);
    }

    @Override
    public Company verifyToken(VerifyCompanyTokenDto dto) {
        String token = dto.getToken();

        String email = Jwts
                .parser()
                .setSigningKey(appProperties.getTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (email == null) {
           throw new InvalidCompanyAuthToken();
        }

        Company company = companiesClient.findByEmail(email);

        if(company == null) {
            throw new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_EMAIL);
        }

        return company;
    }
}
