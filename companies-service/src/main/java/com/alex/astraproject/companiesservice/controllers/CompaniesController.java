package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.PaginationDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;

    @GetMapping
    public PaginatedResponse<CompanyEntity> findMany(PaginationDto dto) {
        return companiesService.findMany(dto);
    }

    @GetMapping("{id}")
    public CompanyEntity findById(@PathVariable long id) {
        CompanyEntity companyEntity = companiesService.findById(id);
        if(companyEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, Errors.COMPANY_NOT_FOUND_BY_ID);
        }

        return companyEntity;
    }

    @PostMapping
    public CompanyEntity createOne(@RequestBody @Valid CreateCompanyDto dto) {
        return companiesService.createOne(dto);
    }

    @PutMapping("{id}")
    public CompanyEntity updateById(@PathVariable long id, @Valid UpdateCompanyDto dto) {
        return companiesService.updateById(id, dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeById(@PathVariable long id) {
        companiesService.removeById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
