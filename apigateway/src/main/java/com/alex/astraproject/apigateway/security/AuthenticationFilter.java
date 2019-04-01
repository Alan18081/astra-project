package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.apigateway.dto.companies.CompanyLoginDto;
import com.alex.astraproject.apigateway.entities.Company;
import com.alex.astraproject.apigateway.feign.clients.CompaniesClient;
import com.alex.astraproject.apigateway.shared.AppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final CompaniesClient companiesClient;
    private final AppProperties appProperties;

    public AuthenticationFilter(AuthenticationManager manager, ApplicationContext context) {
        authenticationManager = manager;
        companiesClient = context.getBean(CompaniesClient.class);
        appProperties = context.getBean(AppProperties.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CompanyLoginDto credentials = new ObjectMapper().readValue(request.getInputStream(), CompanyLoginDto.class);

            return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      credentials.getEmail(),
                      credentials.getPassword(),
                      new ArrayList<>()
              )
            );
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid ");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((Company) authResult.getPrincipal()).getCorporateEmail();
        Company company = companiesClient.findByEmail(email);

        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, appProperties.getTokenSecret())
                .compact();

        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new CompanyLoginResponse(token, SecurityConstants.EXPIRATION_TIME, company)));

        if(company == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Company is not found");
        }
    }
}
