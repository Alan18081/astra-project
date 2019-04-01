package com.alex.astraproject.companiesservice.services;

import com.alex.astraproject.companiesservice.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.companiesservice.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.companiesservice.entities.Employee;

import java.util.List;

public interface EmployeesService {

    List<Employee> findMany(long companyId);

    Employee findById(long id);

    Employee createOne(CreateEmployeeDto dto);

    Employee updateOne(long id, UpdateEmployeeDto dto);

    void removeOne(long id);

}
