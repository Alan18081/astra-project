package com.alex.astraproject.companiesservice.services.impl;

import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.companiesservice.exceptions.Messages;
import com.alex.astraproject.companiesservice.exceptions.NotFoundException;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import com.alex.astraproject.companiesservice.repositories.EmployeesRepository;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Override
    public EmployeeEntity createOne(CreateEmployeeDto dto) {
        EmployeeEntity employee = new EmployeeEntity();
        return employeesRepository.save(employee);
    }

    @Override
    public EmployeeEntity updateById(long id, UpdateEmployeeDto dto) {
        Optional<EmployeeEntity> result = employeesRepository.findById(id);
        if (!result.isPresent()) {
            throw new NotFoundException(Messages.EMPLOYEE_NOT_FOUND);
        }
        EmployeeEntity employee = result.get();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());

        return employeesRepository.save(employee);
    }

    @Override
    public void removeOne(long id) {
        employeesRepository.deleteById(id);
    }

    @Override
    public List<EmployeeEntity> findMany(long companyId) {
        return employeesRepository.findAllByCompanyId(companyId);
    }

    @Override
    public EmployeeEntity findById(long id) {
        Optional<EmployeeEntity> result = employeesRepository.findById(id);
        if(!result.isPresent()) {
            throw new NotFoundException(Messages.EMPLOYEE_NOT_FOUND);
        }

        return result.get();
    }
}
