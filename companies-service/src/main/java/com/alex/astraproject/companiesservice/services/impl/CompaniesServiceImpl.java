package com.alex.astraproject.companiesservice.services.impl;


import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import org.springframework.stereotype.Service;

@Service
public class CompaniesServiceImpl implements CompaniesService {
    @Override
    public CompanyEntity createOne(CreateCompanyDto dto) {
        return null;
    }

    @Override
    public CompanyEntity findById(long id) {
        return null;
    }

    @Override
    public CompanyEntity updateById(long id, UpdateCompanyDto dto) {
        return null;
    }

    @Override
    public void removeById(long id) {

    }
}
