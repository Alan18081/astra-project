package com.alex.astraproject.apigateway.dispatchers.impl;

import com.alex.astraproject.apigateway.dispatchers.EmployeeProcessor;
import com.alex.astraproject.apigateway.dispatchers.gateways.EmployeesGateway;
import com.alex.astraproject.apigateway.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeesDispatcherImpl {

    @Autowired
    private EmployeesGateway employeesGateway;

    public List<Employee> findMany(long companyId) {
        List<Employee> result = employeesGateway.findManyEmployees(companyId);
        System.out.println("Some result" + result);
        return new ArrayList<>();
    }
}
