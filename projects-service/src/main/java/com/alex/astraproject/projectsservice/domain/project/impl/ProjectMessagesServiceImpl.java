package com.alex.astraproject.projectsservice.domain.project.impl;

import com.alex.astraproject.projectsservice.domain.project.ProjectEventEntity;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventsProcessor;
import com.alex.astraproject.projectsservice.domain.project.ProjectMessagesService;
import com.alex.astraproject.projectsservice.shared.AbstractMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(ProjectEventsProcessor.class)
public class ProjectMessagesServiceImpl extends AbstractMessagesService<ProjectEventEntity> implements ProjectMessagesService {

    @Autowired
    private ProjectEventsProcessor processor;

    public void sendCreatedEvent(ProjectEventEntity event) {
        processor.created().send(createMessage(event));
    }

    public void sendDeletedEvent(ProjectEventEntity event) {
        processor.deleted().send(createMessage(event));
    }

    public void sendUpdatedEvent(ProjectEventEntity event) {
        processor.updated().send(createMessage(event));
    }

    @Override
    public void sendCompletedEvent(ProjectEventEntity event) {
        processor.completed().send(createMessage(event));
    }

    @Override
    public void sendStoppedEvent(ProjectEventEntity event) {
        processor.stopped().send(createMessage(event));
    }

    @Override
    public void sendAddedParticipantEvent(ProjectEventEntity event) {
        processor.addedParticipant().send(createMessage(event));
    }

    @Override
    public void sendRemovedParticipantEvent(ProjectEventEntity event) {
        processor.removedParticipant().send(createMessage(event));
    }
}
