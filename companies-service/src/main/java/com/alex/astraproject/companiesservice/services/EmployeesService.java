package com.alex.astraproject.companiesservice.services;


import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Employee;

import java.util.List;

public interface EmployeesService {

    List<EmployeeEntity> findMany(long companyId);

    EmployeeEntity findById(long id);

    EmployeeEntity createOne(CreateEmployeeDto dto);

    EmployeeEntity updateById(long id, UpdateEmployeeDto dto);

    void removeOne(long id);

}
