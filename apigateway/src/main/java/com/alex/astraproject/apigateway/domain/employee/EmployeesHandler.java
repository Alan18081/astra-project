package com.alex.astraproject.apigateway.domain.employee;

import com.alex.astraproject.apigateway.domain.employee.dto.messages.CreatedEmployeeEventDto;
import com.alex.astraproject.shared.events.EmployeeEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableBinding(EmployeesProcessor.class)
public class EmployeesHandler {

    @Autowired
    private EmployeesService employeesService;

    @StreamListener(EmployeeEventType.CREATED)
    public void createdEmployee(@Payload CreatedEmployeeEventDto dto) {
        employeesService.createEmployee(dto);
    }

}
