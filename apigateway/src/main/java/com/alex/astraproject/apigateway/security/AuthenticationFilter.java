package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.shared.SharedContext;
import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.feign.clients.AuthClient;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
//    private final CompaniesClient companiesClient;
    private final SharedContext.AuthClient authClient;

    public AuthenticationFilter(AuthenticationManager manager, ApplicationContext context) {
        authenticationManager = manager;
//        companiesClient = context.getBean(CompaniesClient.class);
        authClient = context.getBean(SharedContext.AuthClient.class);
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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        CompanyLoginDto dto = ((CompanyLoginDto) authResult.getPrincipal());

        JwtCompanyResponse jwtCompanyResponse = authClient.loginCompany(dto);

        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtCompanyResponse));
    }
}
