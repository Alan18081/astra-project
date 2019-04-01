package com.alex.astraproject.apigateway.dispatchers.gateways;

import com.alex.astraproject.apigateway.dispatchers.EmployeeProcessor;
import com.alex.astraproject.apigateway.entities.Employee;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@MessagingGateway
public interface EmployeesGateway {

    @Gateway(requestChannel = "enrich", replyChannel = EmployeeProcessor.RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
    List<Employee> findManyEmployees(long companyId);

}
