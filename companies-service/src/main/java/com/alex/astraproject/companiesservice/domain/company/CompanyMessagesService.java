package com.alex.astraproject.companiesservice.domain.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(CompanyEventsProcessor.class)
public class CompanyMessagesService {

    @Autowired
    private CompanyEventsProcessor processor;

    public void sendCreatedEvent(CompanyEventEntity event) {
        System.out.println("Creating event sending");
        processor.created().send(createMessage(event));
    }

    public void sendDeletedEvent(CompanyEventEntity event) {
        processor.deleted().send(createMessage(event));
    }

    public void sendUpdatedEvent(CompanyEventEntity event) {
        processor.updated().send(createMessage(event));
    }

    private Message<CompanyEventEntity> createMessage(CompanyEventEntity eventEntity) {
        return MessageBuilder.withPayload(eventEntity).build();
    }
 }
