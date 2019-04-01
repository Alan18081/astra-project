package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.companiesservice.entities.Employee;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public List<Employee> findManyByCompany(@RequestParam("companyId") long companyId) {
        return employeesService.findMany(companyId);
    }

    @PostMapping
    public Employee createEmployee(@Valid CreateEmployeeDto dto) {
        return employeesService.createOne(dto);
    }

}
