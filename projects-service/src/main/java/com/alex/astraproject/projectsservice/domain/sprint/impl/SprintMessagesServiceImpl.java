package com.alex.astraproject.projectsservice.domain.sprint.impl;

import com.alex.astraproject.projectsservice.domain.sprint.SprintEventEntity;
import com.alex.astraproject.projectsservice.domain.sprint.SprintEventsProcessor;
import com.alex.astraproject.projectsservice.domain.sprint.SprintMessagesService;
import com.alex.astraproject.projectsservice.shared.AbstractMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(SprintEventsProcessor.class)
public class SprintMessagesServiceImpl extends AbstractMessagesService<SprintEventEntity> implements SprintMessagesService {

    @Autowired
    private SprintEventsProcessor processor;

    public void sendCreatedEvent(SprintEventEntity event) {
        processor.created().send(createMessage(event));
    }

    public void sendDeletedEvent(SprintEventEntity event) {
        processor.deleted().send(createMessage(event));
    }

    public void sendCompletedEvent(SprintEventEntity event) {
        processor.deleted().send(createMessage(event));
    }

    public void sendUpdatedEvent(SprintEventEntity event) {
        processor.updated().send(createMessage(event));
    }
}
