package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.dto.companies.CreateCompanyDto;
import com.alex.astraproject.companiesservice.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.companiesservice.entities.Company;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;

    @GetMapping("{id}")
    public Company findById(@PathVariable long id) {
        return companiesService.findById(id);
    }

    @PostMapping
    public Company createOne(@Valid CreateCompanyDto dto) {
        return companiesService.createOne(dto);
    }

    @PutMapping("{id}")
    public Company updateById(@PathVariable long id, @Valid UpdateCompanyDto dto) {
        return companiesService.updateById(id, dto);
    }

    @DeleteMapping("{id}")
    public void removeById(@PathVariable long id) {
        companiesService.removeById(id);
    }

}
