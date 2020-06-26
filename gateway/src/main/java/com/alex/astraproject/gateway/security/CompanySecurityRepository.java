package com.alex.astraproject.gateway.security;

import com.alex.astraproject.gateway.exceptions.JwtTokenExtractException;
import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.gateway.shared.Utils;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.HttpErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class CompanySecurityRepository implements ServerSecurityContextRepository {

	@Autowired
	private AuthService<Company> companyAuthService;

	@Override
	public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
		return null;
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
		String token = Utils.parseAuthToken(serverWebExchange);
		return companyAuthService.verifyToken(token)
			.map(company -> {
				Authentication authentication = new UsernamePasswordAuthenticationToken(company, new ArrayList<>());
				return (SecurityContext) new SecurityContextImpl(authentication);
			})
			.switchIfEmpty(Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_EMAIL)));
	}
}
