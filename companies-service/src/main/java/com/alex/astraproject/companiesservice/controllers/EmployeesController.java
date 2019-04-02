package com.alex.astraproject.companiesservice.controllers;

import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
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
    public List<EmployeeEntity> findManyByCompany(@RequestParam("companyId") long companyId) {
        return employeesService.findMany(companyId);
    }

    @PostMapping
    public EmployeeEntity createEmployee(@Valid CreateEmployeeDto dto) {
        return employeesService.createOne(dto);
    }

}
