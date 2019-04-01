package com.alex.astraproject.apigateway.dispatchers;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeeProcessor {
    String FIND_MANY_EMPLOYEES_BY_COMPANY = "FIND_MANY_EMPLOYEES_BY_COMPANY";
    String RES_FIND_MANY_EMPLOYEES_BY_COMPANY = "RES_FIND_MANY_EMPLOYEES_BY_COMPANY";
    String CREATE_EMPLOYEE = "[CompaniesService] Create Employee";
    String CREATED_EMPLOYEE = "[CompaniesService] Created Employee";

    @Input(CREATE_EMPLOYEE)
    SubscribableChannel createdEmployee();

    @Output(CREATED_EMPLOYEE)
    MessageChannel createEmployee();

    @Output(FIND_MANY_EMPLOYEES_BY_COMPANY)
    MessageChannel findManyEmployeesByCompany();

    @Input(RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
    SubscribableChannel foundManyEmployeesByCompany();
}
