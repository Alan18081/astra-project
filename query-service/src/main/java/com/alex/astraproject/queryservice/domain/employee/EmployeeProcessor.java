package com.alex.astraproject.queryservice.domain.employee;

import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeeProcessor {

    @Input(EmployeeEventType.CREATED)
    SubscribableChannel created();

    @Input(EmployeeEventType.UPDATED)
    SubscribableChannel updated();

}
