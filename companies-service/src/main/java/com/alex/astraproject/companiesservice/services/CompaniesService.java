package com.alex.astraproject.companiesservice.services;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.PaginationDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.data.domain.Page;

public interface CompaniesService {

    PaginatedResponse<CompanyEntity> findMany(PaginationDto dto);

    CompanyEntity createOne(CreateCompanyDto dto);

    CompanyEntity findById(long id);

    CompanyEntity updateById(long id, UpdateCompanyDto dto);

    void removeById(long id);

}
