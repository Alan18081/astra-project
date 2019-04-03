package com.alex.astraproject.companiesservice.services;


import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.FindManyEmployeesDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.responses.PaginatedResponse;

import java.util.List;

public interface EmployeesService {

    PaginatedResponse<EmployeeEntity> findManyByCompany(FindManyEmployeesDto dto);

    EmployeeEntity findById(long id);

    EmployeeEntity createOne(CreateEmployeeDto dto);

    EmployeeEntity updateById(long id, UpdateEmployeeDto dto);

    void removeById(long id);

}
