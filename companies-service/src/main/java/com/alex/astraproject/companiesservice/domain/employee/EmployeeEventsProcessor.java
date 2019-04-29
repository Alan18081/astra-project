package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EmployeeEventsProcessor {

    @Output(EmployeeEventType.CREATED)
    MessageChannel created();

    @Output(EmployeeEventType.UPDATED)
    MessageChannel updated();

    @Output(EmployeeEventType.FIRED)
    MessageChannel fired();

    @Output(EmployeeEventType.CHANGED_PASSWORD)
    MessageChannel changedPassword();

}
