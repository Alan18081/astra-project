package com.alex.astraproject.companiesservice.domain.employee.impl;

import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventsProcessor;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(EmployeeEventsProcessor.class)
public class EmployeeMessagesServiceImpl implements EmployeeMessagesService {

    @Autowired
    private EmployeeEventsProcessor processor;

    public void sendCreatedEmployeeEvent(EmployeeEventEntity event) {
        processor.created().send(createMessage(event));
    }

    public void sendUpdatedEmployeeEvent(EmployeeEventEntity event) {
        processor.updated().send(createMessage(event));
    }

    public void sendFiredEmployeeEvent(EmployeeEventEntity event) {
        processor.fired().send(createMessage(event));
    }

    @Override
    public void sendChangedEmployeePositionEvent(EmployeeEventEntity event) {
        processor.changedPosition().send(createMessage(event));
    }

    private Message<EmployeeEventEntity> createMessage(EmployeeEventEntity eventEntity) {
        return MessageBuilder.withPayload(eventEntity).build();
    }

}
