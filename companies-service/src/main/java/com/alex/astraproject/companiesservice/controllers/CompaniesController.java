package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;

    @GetMapping("{id}")
    public CompanyEntity findById(@PathVariable long id) {
        return companiesService.findById(id);
    }

    @PostMapping
    public CompanyEntity createOne(@Valid CreateCompanyDto dto) {
        return companiesService.createOne(dto);
    }

    @PutMapping("{id}")
    public CompanyEntity updateById(@PathVariable long id, @Valid UpdateCompanyDto dto) {
        return companiesService.updateById(id, dto);
    }

    @DeleteMapping("{id}")
    public void removeById(@PathVariable long id) {
        companiesService.removeById(id);
    }

}
