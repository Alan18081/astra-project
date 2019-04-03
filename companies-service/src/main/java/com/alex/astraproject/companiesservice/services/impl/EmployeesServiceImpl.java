package com.alex.astraproject.companiesservice.services.impl;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.companiesservice.services.CompaniesService;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import com.alex.astraproject.companiesservice.repositories.EmployeesRepository;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.FindManyEmployeesDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
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

import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private CompaniesService companiesService;

    @Override
    public PaginatedResponse<EmployeeEntity> findManyByCompany(FindManyEmployeesDto dto) {
        CompanyEntity companyEntity = companiesService.findById(dto.getCompanyId());

        if(companyEntity == null) {
            throw new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID);
        }
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
        Page<EmployeeEntity> employeeEntityPage = employeesRepository.findAllByCompany(companyEntity, pageable);

        return new PaginatedResponse<>(
                employeeEntityPage.getContent(),
                dto.getPage(),
                employeeEntityPage.getNumber(),
                employeeEntityPage.getTotalElements()
        );
    }

    @Override
    public

    @Override
    public EmployeeEntity createOne(CreateEmployeeDto dto) {
        EmployeeEntity employee = new EmployeeEntity();
        return employeesRepository.save(employee);
    }

    @Override
    public EmployeeEntity updateById(long id, UpdateEmployeeDto dto) {
        Optional<EmployeeEntity> result = employeesRepository.findById(id);
        if (!result.isPresent()) {
            throw new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID);
        }

        EmployeeEntity employeeEntity = result.get();
        BeanUtils.copyProperties(dto, employeeEntity);

        return employeesRepository.save(employeeEntity);
    }

    @Override
    public void removeById(long id) {
        employeesRepository.deleteById(id);
    }


    @Override
    public EmployeeEntity findById(long id) {
        Optional<EmployeeEntity> result = employeesRepository.findById(id);
        if(!result.isPresent()) {
            throw new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID);
        }

        return result.get();
    }
}
