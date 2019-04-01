package com.alex.astraproject.companiesservice.handlers;

import com.alex.astraproject.companiesservice.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.companiesservice.entities.Employee;
import com.alex.astraproject.companiesservice.processors.EmployeesProcessor;
import com.alex.astraproject.companiesservice.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

@Configuration
@EnableBinding(EmployeesProcessor.class)
public class EmployeesHandler {

    @Autowired
    private EmployeesService employeesService;

    @StreamListener(EmployeesProcessor.CREATE_EMPLOYEE)
    @SendTo(EmployeesProcessor.CREATED_EMPLOYEE)
    public Employee createEmployee(CreateEmployeeDto dto) {
        return employeesService.createOne(dto);
    }

    @StreamListener(EmployeesProcessor.FIND_MANY_EMPLOYEES_BY_COMPANY)
    @SendTo(EmployeesProcessor.FIND_MANY_EMPLOYEES_BY_COMPANY)
    public List<Employee> findMany(long companyId) {
        System.out.println(companyId);
        System.out.println("Hello from data");
        return null;
    }

}
