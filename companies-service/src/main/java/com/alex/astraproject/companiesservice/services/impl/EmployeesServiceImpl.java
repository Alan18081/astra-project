package com.alex.astraproject.companiesservice.services.impl;

import com.alex.astraproject.companiesservice.exceptions.Messages;
import com.alex.astraproject.companiesservice.exceptions.NotFoundException;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import com.alex.astraproject.companiesservice.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Override
    public Employee createOne(CreateEmployeeDto dto) {
        Employee employee = new Employee();
        return employeesRepository.save(employee);
    }

    @Override
    public Employee updateOne(long id, UpdateEmployeeDto dto) {
        Optional<Employee> result = employeesRepository.findById(id);
        if (!result.isPresent()) {
            throw new NotFoundException(Messages.EMPLOYEE_NOT_FOUND);
        }
        Employee employee = result.get();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());

        return employeesRepository.save(employee);
    }

    @Override
    public void removeOne(long id) {
        employeesRepository.deleteById(id);
    }

    @Override
    public List<Employee> findMany(long companyId) {
        return employeesRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Employee findById(long id) {
        Optional<Employee> result = employeesRepository.findById(id);
        if(!result.isPresent()) {
            throw new NotFoundException(Messages.EMPLOYEE_NOT_FOUND);
        }

        return result.get();
    }
}
