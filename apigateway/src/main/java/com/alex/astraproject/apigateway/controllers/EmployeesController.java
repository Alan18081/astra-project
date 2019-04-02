package com.alex.astraproject.apigateway.controllers;

import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
//@RestController
//@RequestMapping("/employees")
public class EmployeesController {

//    @Autowired
//    private EmployeesClient employeesClient;
//
//    @Autowired
//    private Employee employee;
//
//    @GetMapping
//    public List<Employee> findMany(@RequestParam("companyId") long companyId) {
//        return employeesClient.findManyByCompanyId(companyId);
//    }
//
//    @GetMapping("{id}")
//    public Employee findById(@PathVariable long id) {
//        return employeesClient.findById(id);
//    }
//
//    @PostMapping
//    public Employee createOne(@RequestBody CreateEmployeeDto dto) {
//        return employeesClient.createOne(dto);
//    }
//
//    @PutMapping("{id}")
//    public Employee updateById(@PathVariable long id, UpdateEmployeeDto dto) {
//        return employeesClient.updateById(id, dto);
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> removeById(@PathVariable long id) {
//        employeesClient.removeById(id);
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }

}