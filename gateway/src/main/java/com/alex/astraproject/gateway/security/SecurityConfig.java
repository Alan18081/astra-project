package com.alex.astraproject.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
	private CompanySecurityRepository companySecurityRepository;

	@Autowired
	private EmployeeSecurityRepository employeeSecurityRepository;

	private ServerHttpSecurity commonConfig(ServerHttpSecurity security) {
		return security
			.exceptionHandling()
			.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
			.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable();
	}

	@Bean
	public SecurityWebFilterChain companySecurityWebFilterChain(ServerHttpSecurity security) {
		return commonConfig(security.securityMatcher(new SecurityPathMatcher("/companies/**")))
			.securityContextRepository(companySecurityRepository)
			.authorizeExchange()
			.pathMatchers(HttpMethod.OPTIONS).permitAll()
			.pathMatchers(HttpMethod.POST, "/companies/login").permitAll()
			.pathMatchers(HttpMethod.POST, "/companies").permitAll()
			.and()
			.securityMatcher(new SecurityPathMatcher("/employees/**"))
			.securityContextRepository(employeeSecurityRepository)
			.authorizeExchange()
			.pathMatchers(HttpMethod.POST, "/employees/login").permitAll()
			.pathMatchers(HttpMethod.POST, "/employees").permitAll()
			.anyExchange().authenticated()
			.and()
			.build();
	}


}
