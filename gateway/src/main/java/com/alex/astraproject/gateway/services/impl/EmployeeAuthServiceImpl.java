package com.alex.astraproject.gateway.services.impl;

import com.alex.astraproject.gateway.clients.CompanyClient;
import com.alex.astraproject.gateway.clients.EmployeeClient;
import com.alex.astraproject.gateway.exceptions.CompanyInvalidPasswordException;
import com.alex.astraproject.gateway.exceptions.JwtTokenExtractException;
import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.gateway.services.JwtService;
import com.alex.astraproject.gateway.services.PasswordService;
import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.exceptions.common.InvalidPasswordException;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.exceptions.companies.InvalidCompanyAuthToken;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.JwtResponse;
import com.alex.astraproject.shared.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeAuthServiceImpl implements AuthService<Employee> {

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<JwtResponse<Employee>> createToken(LoginDto dto) {
        return employeeClient.findEmployeeByEmail(dto.getEmail())
          .switchIfEmpty(Mono.error(new NotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_EMAIL)))
          .flatMap(employee -> {
              System.out.println(dto.getPassword() + " : " + employee.getPassword());
            if(!passwordService.comparePassword(dto.getPassword(), employee.getPassword())) {
              return Mono.error(new InvalidPasswordException(Errors.INVALID_EMPLOYEE_PASSWORD));
            }
              System.out.println("Valid password");
            return Mono.just(employee);
          })
          .flatMap(employee -> {
              String token = jwtService.generateToken(employee);
              return Mono.just(new JwtResponse<>(token, SecurityConstants.EXPIRATION_TIME, employee));
          });
    }

    @Override
    public Mono<Employee> verifyToken(String token) {
        if(token == null) {
            return Mono.error(new JwtTokenExtractException(Errors.INVALID_COMPANY_AUTH_TOKEN));
        }
        String email = jwtService.getUsernameFromToken(token);

        if (email == null) {
           throw new InvalidCompanyAuthToken();
        }

        return employeeClient.findEmployeeByEmail(email);
    }
}
