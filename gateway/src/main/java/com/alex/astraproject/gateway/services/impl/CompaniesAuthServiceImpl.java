package com.alex.astraproject.gateway.services.impl;

import com.alex.astraproject.gateway.clients.CompanyClient;
import com.alex.astraproject.gateway.services.CompaniesAuthService;
import com.alex.astraproject.gateway.shared.AppProperties;
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
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class CompaniesAuthServiceImpl implements CompaniesAuthService {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private CompanyClient companyClient;

    @Override
    public Mono<JwtCompanyResponse> createToken(CompanyLoginDto dto) {
        return companyClient.findCompanyByEmail(dto.getEmail())
          .flatMap(company -> {
              String token = Jwts.builder()
                .setSubject(company.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, appProperties.getTokenSecret())
                .compact();

              return Mono.just(new JwtCompanyResponse(token, SecurityConstants.EXPIRATION_TIME, company));
          });
    }

    @Override
    public Mono<Company> verifyToken(VerifyCompanyTokenDto dto) {
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

        return companyClient.findCompanyByEmail(email);
    }
}
