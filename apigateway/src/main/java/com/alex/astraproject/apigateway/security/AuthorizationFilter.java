package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.apigateway.clients.AuthClient;
import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.security.SecurityConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthClient authClient;

    public AuthorizationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        super(authenticationManager);
        this.authClient = context.getBean(AuthClient.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_NAME);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");

        Company company = authClient.verifyToken(new VerifyCompanyTokenDto(token));

        if(company != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(company, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
