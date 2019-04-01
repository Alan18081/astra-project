package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.apigateway.shared.AppProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final AppProperties appProperties;

    public AuthorizationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        super(authenticationManager);
        appProperties = context.getBean(AppProperties.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_NAME);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");

        String email = Jwts.parser()
                .setSigningKey(appProperties.getTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if(email != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
