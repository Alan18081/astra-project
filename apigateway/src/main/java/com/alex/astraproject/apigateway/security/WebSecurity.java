package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.apigateway.clients.AuthClient;
import com.alex.astraproject.shared.SharedContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Import(SharedContext.class)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_COMPANY_URL).permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_EMPLOYEE_URL).permitAll()
            .anyRequest().authenticated()
            .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), context))
                .addFilter(new AuthorizationFilter(authenticationManager()))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
