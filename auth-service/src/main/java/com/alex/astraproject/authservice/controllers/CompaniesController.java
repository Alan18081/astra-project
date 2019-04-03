package com.alex.astraproject.authservice.controllers;

import com.alex.astraproject.authservice.clients.CompaniesClient;
import com.alex.astraproject.authservice.services.CompaniesAuthService;
import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompaniesController {

    @Autowired
    private CompaniesClient companiesClient;

    @Autowired
    private CompaniesAuthService companiesAuthService;

    @PostMapping("/login")
    public JwtCompanyResponse loginCompany(@RequestBody @Valid CompanyLoginDto dto) {
        Company company = companiesClient.findByEmail(dto.getEmail());

        return companiesAuthService.createToken(company);
    }

    @PostMapping("verifyToken")
    public Company verifyToken(@RequestBody @Valid VerifyCompanyTokenDto dto) {
        return companiesAuthService.verifyToken(dto);
    }

}
