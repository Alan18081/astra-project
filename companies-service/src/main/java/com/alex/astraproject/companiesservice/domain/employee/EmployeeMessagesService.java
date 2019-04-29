package com.alex.astraproject.companiesservice.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(EmployeeEventsProcessor.class)
public class EmployeeMessagesService {

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

    private Message<EmployeeEventEntity> createMessage(EmployeeEventEntity eventEntity) {
        return MessageBuilder.withPayload(eventEntity).build();
    }

}
