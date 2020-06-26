package com.alex.astraproject.gateway.security;

import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.gateway.shared.Utils;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class EmployeeSecurityRepository implements ServerSecurityContextRepository {

	@Autowired
	private AuthService<Employee> employeeAuthService;

	@Override
	public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
		return null;
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
		String token = Utils.parseAuthToken(serverWebExchange);
		return employeeAuthService.verifyToken(token)
			.map(company -> {
				Authentication authentication = new UsernamePasswordAuthenticationToken(company, new ArrayList<>());
				return (SecurityContext) new SecurityContextImpl(authentication);
			})
			.switchIfEmpty(Mono.error(new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_EMAIL)));
	}
}
