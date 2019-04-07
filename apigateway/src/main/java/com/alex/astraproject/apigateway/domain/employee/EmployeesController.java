package com.alex.astraproject.apigateway.domain.employee;

import com.alex.astraproject.shared.dto.employees.FindManyEmployeesDto;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public PaginatedResponse<EmployeeAggregate> findMany(FindManyEmployeesDto dto) {
        return employeesService.findMany(dto);
    }

    @GetMapping("{id}")
    public EmployeeAggregate findById(@PathVariable UUID id) {
        return employeesService.findById(id);
    }

}
