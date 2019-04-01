package com.alex.astraproject.companiesservice.processors;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeesProcessor {
    String FIND_MANY_EMPLOYEES_BY_COMPANY = "[CompaniesService] Find Many Employees By Company";
    String RES_FIND_MANY_EMPLOYEES_BY_COMPANY = "[CompaniesService] Res: Find Many Employees By Company";
    String CREATE_EMPLOYEE = "[CompaniesService] Create Employee";
    String CREATED_EMPLOYEE = "[CompaniesService] Created Employee";

    @Input(CREATE_EMPLOYEE)
    SubscribableChannel createEmployee();

    @Output(CREATED_EMPLOYEE)
    MessageChannel createdEmployee();

    @Output(RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
    MessageChannel foundManyEmployeesByCompany();

    @Input(FIND_MANY_EMPLOYEES_BY_COMPANY)
    SubscribableChannel findManyEmployeesByCompany();

}
