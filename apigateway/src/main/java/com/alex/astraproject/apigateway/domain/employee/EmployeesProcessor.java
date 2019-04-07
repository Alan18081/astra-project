package com.alex.astraproject.apigateway.domain.employee;

import com.alex.astraproject.shared.events.EmployeeEventType;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EmployeesProcessor {

    @Input(EmployeeEventType.CREATED)
    SubscribableChannel created();

    @Input(EmployeeEventType.UPDATED)
    SubscribableChannel updated();

    @Input(EmployeeEventType.DELETED)
    SubscribableChannel deleted();

    @Input(EmployeeEventType.CHANGED_PASSWORD)
    SubscribableChannel changedPassword();

}
