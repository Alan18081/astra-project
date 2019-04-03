package com.alex.astraproject.companiesservice.services.impl;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.companiesservice.entities.PositionEntity;
import com.alex.astraproject.companiesservice.repositories.EmployeesRepository;
import com.alex.astraproject.companiesservice.repositories.PositionsRepository;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import com.alex.astraproject.companiesservice.services.PositionsService;
import com.alex.astraproject.shared.dto.companies.UpdateCompanyDto;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.dto.positions.CreatePositionDto;
import com.alex.astraproject.shared.dto.positions.FindManyPositionsDto;
import com.alex.astraproject.shared.dto.positions.UpdatePositionDto;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionsServiceImpl implements PositionsService {

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private CompaniesService companiesService;

    @Override
    public PositionEntity createOne(CreatePositionDto dto) {

        PositionEntity positionEntity = new PositionEntity();
        BeanUtils.copyProperties(dto, positionEntity);
        return positionsRepository.save(positionEntity);
    }

    @Override
    public PositionEntity updateById(long id, UpdatePositionDto dto) {
        Optional<PositionEntity> result = positionsRepository.findById(id);
        if (!result.isPresent()) {
            throw new EmployeeNotFoundException(Errors.POSITION_NOT_FOUND_BY_ID);
        }

        PositionEntity positionEntity = result.get();
        BeanUtils.copyProperties(dto, positionEntity);

        return positionsRepository.save(positionEntity);
    }

    @Override
    public void removeById(long id) {
        positionsRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<PositionEntity> findManyByCompany(FindManyPositionsDto dto) {
        CompanyEntity companyEntity = companiesService.findById(dto.getCompanyId());

        if(companyEntity == null) {
            throw new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID);
        }
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
        Page<PositionEntity> positionEntityPage =  positionsRepository.findAllByCompanyEntity(companyEntity, pageable);

        return new PaginatedResponse<>(
                positionEntityPage.getContent(),
                dto.getPage(),
                positionEntityPage.getNumber(),
                positionEntityPage.getTotalElements()
        );
    }

    @Override
    public PositionEntity findById(long id) {
        Optional<PositionEntity> result = positionsRepository.findById(id);
        return result.orElseThrow(() -> new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID));
    }
}
