package com.alex.astraproject.companiesservice.domain.position.impl;

import com.alex.astraproject.companiesservice.domain.position.PositionEventEntity;
import com.alex.astraproject.companiesservice.domain.position.PositionEventsProcessor;
import com.alex.astraproject.companiesservice.domain.position.PositionMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(PositionEventsProcessor.class)
public class PositionMessagesServiceImpl implements PositionMessagesService {

    @Autowired
    private PositionEventsProcessor processor;

    @Override
    public void sendCreatedPositionEvent(PositionEventEntity event) {
        processor.created().send(createMessage(event));
    }

    @Override
    public void sendUpdatedPositionEvent(PositionEventEntity event) {
        processor.updated().send(createMessage(event));
    }

    @Override
    public void sendDeletedPositionEvent(PositionEventEntity event) {
        processor.fired().send(createMessage(event));
    }

    private Message<PositionEventEntity> createMessage(PositionEventEntity eventEntity) {
        return MessageBuilder.withPayload(eventEntity).build();
    }

}
