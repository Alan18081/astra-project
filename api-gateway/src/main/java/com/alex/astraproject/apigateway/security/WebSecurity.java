package com.alex.astraproject.apigateway.security;

import com.alex.astraproject.shared.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_COMPANY_URL).permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_COMPANY_URL).authenticated()
                .and().addFilterBefore(new AuthenticationFilter(authenticationManager(), context), UsernamePasswordAuthenticationFilter.class)
            .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_EMPLOYEE_URL).permitAll()

                .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_COMPANY_URL).authenticated()
                .and().addFilterBefore(new AuthenticationFilter(authenticationManager(), context), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), context))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) {
        web.ignoring().mvcMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_COMPANY_URL);
    }
}
