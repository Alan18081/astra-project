package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.domain.EventType;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.PaginationDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

@RestController
@RequestMapping("/companies")
@EnableBinding(Source.class)
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;

    private Source source;

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
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createOne(@RequestBody @Valid CreateCompanyDto dto) {
        CompanyEntity company = companiesService.createOne(dto);
        CompanyEvent event = new CompanyEvent(company, EventType.COMPANY_CREATED);
        source.output().send(MessageBuilder.withPayload(event).build());
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
