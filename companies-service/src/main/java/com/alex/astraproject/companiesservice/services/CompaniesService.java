package com.alex.astraproject.companiesservice.services;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;

public interface CompaniesService {

    CompanyEntity createOne(CreateCompanyDto dto);

    CompanyEntity findById(long id);

    CompanyEntity updateById(long id, UpdateCompanyDto dto);

    void removeById(long id);

}
