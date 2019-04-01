package com.alex.astraproject.apigateway.controllers;

import com.alex.astraproject.apigateway.dispatchers.impl.EmployeesDispatcherImpl;
import com.alex.astraproject.apigateway.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesDispatcherImpl employeesDispatcher;

    @GetMapping
    public DeferredResult<ResponseEntity<String>> findMany(@RequestParam("companyId") long companyId) {
        DeferredResult<ResponseEntity<String>> output = new DeferredResult<>();
        employeesDispatcher.findMany(companyId, output);
        return output;
    }

}
