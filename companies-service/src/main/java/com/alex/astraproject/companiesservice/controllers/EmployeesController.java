package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.FindManyEmployeesDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public PaginatedResponse<EmployeeEntity> findManyByCompany(FindManyEmployeesDto dto) {
        return employeesService.findManyByCompany(dto);
    }

    @PostMapping
    public EmployeeEntity createOne(@RequestBody @Valid CreateEmployeeDto dto) {
        return employeesService.createOne(dto);
    }

    @PutMapping("{id}")
    public EmployeeEntity updateById(@PathVariable long id, @RequestBody @Valid UpdateEmployeeDto dto) {
        EmployeeEntity employee = employeesService.updateById(id, dto);
        if(employee == null) {
            throw new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID);
        }

        return employee;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeById(@PathVariable long id) {
        employeesService.removeById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
