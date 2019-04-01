package com.alex.astraproject.companiesservice.services.impl;

import com.alex.astraproject.companiesservice.dto.companies.CreateCompanyDto;
import com.alex.astraproject.companiesservice.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.companiesservice.entities.Company;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import org.springframework.stereotype.Service;

@Service
public class CompaniesServiceImpl implements CompaniesService {
    @Override
    public Company createOne(CreateCompanyDto dto) {
        return null;
    }

    @Override
    public Company findById(long id) {
        return null;
    }

    @Override
    public Company updateById(long id, UpdateCompanyDto dto) {
        return null;
    }

    @Override
    public void removeById(long id) {

    }
}
