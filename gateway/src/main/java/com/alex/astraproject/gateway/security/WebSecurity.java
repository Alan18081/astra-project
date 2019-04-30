package com.alex.astraproject.gateway.security;

import com.alex.astraproject.shared.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
public class WebSecurity {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_COMPANY_URL).permitAll()
            .pathMatchers(HttpMethod.POST, SecurityConstants.LOGIN_EMPLOYEE_URL).permitAll()
            .and()
                .addFilterAt()
            .anyExchange().authenticated()
            .and().build();
    }
}
