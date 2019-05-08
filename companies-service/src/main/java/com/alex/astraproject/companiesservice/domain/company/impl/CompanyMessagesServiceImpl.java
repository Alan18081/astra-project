package com.alex.astraproject.companiesservice.domain.company.impl;

import com.alex.astraproject.companiesservice.domain.company.CompanyEventEntity;
import com.alex.astraproject.companiesservice.domain.company.CompanyEventsProcessor;
import com.alex.astraproject.companiesservice.domain.company.CompanyMessagesService;
import com.alex.astraproject.companiesservice.shared.AbstractMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(CompanyEventsProcessor.class)
public class CompanyMessagesServiceImpl extends AbstractMessagesService<CompanyEventEntity> implements CompanyMessagesService {

    @Autowired
    private CompanyEventsProcessor processor;

    @Override
    public void sendCreatedEvent(CompanyEventEntity event) {
        processor.created().send(createMessage(event));
    }

    @Override
    public void sendDeletedEvent(CompanyEventEntity event) {
        processor.deleted().send(createMessage(event));
    }

    @Override
    public void sendUpdatedEvent(CompanyEventEntity event) {
        processor.updated().send(createMessage(event));
    }


 }
