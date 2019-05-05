package com.alex.astraproject.gateway.security;

import com.alex.astraproject.shared.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

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
			.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> {
				swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			}))
			.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> {
				swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			}))
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable();
//			.authorizeExchange()
//			.pathMatchers(HttpMethod.OPTIONS).permitAll()
//			.pathMatchers(HttpMethod.POST, SecurityConstants.LOGIN_COMPANY_URL).permitAll()
//			.pathMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_COMPANY_URL).permitAll()
//			.pathMatchers(HttpMethod.POST, SecurityConstants.LOGIN_EMPLOYEE_URL).permitAll()
//			.pathMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_EMPLOYEE_URL).permitAll()
//			.and();
	}

//	@Bean
//	public SecurityWebFilterChain employeeSecurityWebFilterChain(ServerHttpSecurity security) {
//			return commonConfig(security.securityMatcher(new SecurityPathMatcher("/companies-service/employees/**")))
//			.securityContextRepository(employeeSecurityRepository)
//			.authorizeExchange()
////			.pathMatchers(HttpMethod.OPTIONS).permitAll()
////			.pathMatchers(HttpMethod.POST, SecurityConstants.LOGIN_EMPLOYEE_URL).permitAll()
////			.pathMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_EMPLOYEE_URL).permitAll()
//			.anyExchange().authenticated().and().build();
//	}

	@Bean
	public SecurityWebFilterChain companySecurityWebFilterChain(ServerHttpSecurity security) {
		return commonConfig(security.securityMatcher(new SecurityPathMatcher("/companies-service/companies/**")))
			.securityContextRepository(companySecurityRepository)
			.authorizeExchange()
			.pathMatchers(HttpMethod.OPTIONS).permitAll()
			.pathMatchers(HttpMethod.POST, SecurityConstants.LOGIN_COMPANY_URL).permitAll()
			.pathMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_COMPANY_URL).permitAll()
			.and()
			.securityMatcher(new SecurityPathMatcher("/companies-service/employees/**"))
			.securityContextRepository(employeeSecurityRepository)
			.authorizeExchange()
			.pathMatchers(HttpMethod.POST, "/companies-service/employees/login").permitAll()
			.pathMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_EMPLOYEE_URL).permitAll()
			.anyExchange().authenticated()
			.and()
			.build();
	}


}
