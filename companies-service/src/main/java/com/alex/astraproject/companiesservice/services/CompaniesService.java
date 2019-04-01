package com.alex.astraproject.companiesservice.services;

import com.alex.astraproject.companiesservice.dto.companies.CreateCompanyDto;
import com.alex.astraproject.companiesservice.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.companiesservice.entities.Company;

public interface CompaniesService {

    Company createOne(CreateCompanyDto dto);

    Company findById(long id);

    Company updateById(long id, UpdateCompanyDto dto);

    void removeById(long id);

}
