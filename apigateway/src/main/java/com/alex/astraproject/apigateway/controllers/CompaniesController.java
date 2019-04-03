package com.alex.astraproject.apigateway.controllers;

import com.alex.astraproject.apigateway.clients.CompaniesClient;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.shared.entities.Company;
//import com.alex.astraproject.shared.feign.clients.CompaniesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    private CompaniesClient companiesClient;

    @GetMapping
    public List<Company> findMany(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return companiesClient.findMany(page, limit);
    }

    @GetMapping("/{id}")
    public Company findById(@PathVariable long id) {
        return companiesClient.findById(id);
    }

    @PostMapping("")
    public Company createOne(@RequestBody CreateCompanyDto dto) {
        return companiesClient.createOne(dto);
    }

    @PutMapping("/{id}")
    public Company updateById(@PathVariable long id, @RequestBody UpdateCompanyDto dto) {
        return companiesClient.updateById(id, dto);
    }
}

