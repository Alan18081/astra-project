package com.alex.astraproject.authservice.controllers;

import com.alex.astraproject.shared.dto.companies.CompanyLoginDto;
import com.alex.astraproject.shared.responses.JwtCompanyResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class CompaniesController {

    @PostMapping("/login")
    public JwtCompanyResponse loginCompany(@RequestBody @Valid CompanyLoginDto dto) {

    }

}
