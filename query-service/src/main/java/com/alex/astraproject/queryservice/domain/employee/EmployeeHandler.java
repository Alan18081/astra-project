package com.alex.astraproject.queryservice.domain.employee;

import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.events.CompanyEvent;
import com.alex.astraproject.shared.events.EmployeeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@EnableBinding(EmployeeProcessor.class)
public class EmployeeHandler {

    @Autowired
    private EmployeeService employeeService;

    @StreamListener(EmployeeEventType.CREATED)
    public void onCompanyCreated(@Payload EmployeeEvent employeeEvent) {
        System.out.println("Creating employee: " + employeeEvent);
        employeeService.createOne(employeeEvent);
    }

    @StreamListener(EmployeeEventType.UPDATED)
    public void onCompanyUpdated(@Payload EmployeeEvent employeeEvent) {
        employeeService.updateById(employeeEvent);
    }

}
