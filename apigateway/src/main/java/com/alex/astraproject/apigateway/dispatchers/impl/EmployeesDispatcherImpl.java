package com.alex.astraproject.apigateway.dispatchers.impl;

import com.alex.astraproject.apigateway.dispatchers.EmployeeProcessor;
import com.alex.astraproject.apigateway.dispatchers.gateways.EmployeesGateway;
import com.alex.astraproject.apigateway.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.converter.MessageConverterUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableBinding(EmployeeProcessor.class)
public class EmployeesDispatcherImpl {

    @Autowired
    private EmployeeProcessor processor;

    @Autowired
    private EmployeesGateway employeesGateway;


    public List<Employee> findMany(long companyId, DeferredResult<ResponseEntity<String>> output) {
        processor.findManyEmployeesByCompany().send(MessageBuilder.withPayload(companyId).build());
        processor.foundManyEmployeesByCompany().subscribe(m -> {
            System.out.println("hello");
            System.out.println(m.getPayload());
            output.setResult(new ResponseEntity<String>(m.getPayload().toString(), HttpStatus.ACCEPTED));
        });
        return new ArrayList<>();
    }
}
