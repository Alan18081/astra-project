package com.alex.astraproject.companiesservice.services.impl;


import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.entities.PositionEntity;
import com.alex.astraproject.companiesservice.repositories.CompaniesRepository;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import com.alex.astraproject.shared.dto.companies.CreateCompanyDto;
import com.alex.astraproject.shared.dto.companies.PaginationDto;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.shared.dto.positions.FindManyPositionsDto;
import com.alex.astraproject.shared.exceptions.companies.CompanyAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompaniesServiceImpl implements CompaniesService {

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public PaginatedResponse<CompanyEntity> findMany(PaginationDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
        Page<CompanyEntity> companyEntityPage = companiesRepository.findAll(pageable);
        return new PaginatedResponse<>(
                companyEntityPage.getContent(),
                dto.getPage(),
                companyEntityPage.getNumber(),
                companyEntityPage.getTotalElements()
        );
    }

    @Override
    public CompanyEntity createOne(CreateCompanyDto dto) {
        Optional<CompanyEntity> foundCompanyResult = companiesRepository.findByCorporateEmail(dto.getCorporateEmail());
        if(foundCompanyResult.isPresent()) {
            throw new CompanyAlreadyExistsException();
        }

        CompanyEntity companyEntity = new CompanyEntity();
        BeanUtils.copyProperties(dto, companyEntity);
        companyEntity.setHashedPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        return companiesRepository.save(companyEntity);
    }

    @Override
    public CompanyEntity findById(long id) {
        Optional<CompanyEntity> result = companiesRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public CompanyEntity updateById(long id, UpdateCompanyDto dto) {
        Optional<CompanyEntity> result = companiesRepository.findById(id);
        if(!result.isPresent()) {
            throw new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID);
        }

        CompanyEntity companyEntity = result.get();
        BeanUtils.copyProperties(dto, companyEntity);

        return companiesRepository.save(companyEntity);
    }

    @Override
    public void removeById(long id) {
        companiesRepository.deleteById(id);
    }
}
