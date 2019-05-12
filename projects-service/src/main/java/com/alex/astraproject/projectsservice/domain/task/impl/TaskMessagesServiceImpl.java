package com.alex.astraproject.projectsservice.domain.task.impl;

import com.alex.astraproject.projectsservice.domain.task.TaskEventsProcessor;
import com.alex.astraproject.projectsservice.domain.task.TaskEventEntity;
import com.alex.astraproject.projectsservice.domain.task.TaskMessagesService;
import com.alex.astraproject.projectsservice.shared.AbstractMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(TaskEventsProcessor.class)
public class TaskMessagesServiceImpl extends AbstractMessagesService<TaskEventEntity> implements TaskMessagesService {

    @Autowired
    private TaskEventsProcessor processor;

    @Override
    public void sendCreatedEvent(TaskEventEntity event) {
        processor.created().send(createMessage(event));
    }

    @Override
    public void sendDeletedEvent(TaskEventEntity event) {
        processor.deleted().send(createMessage(event));
    }

    @Override
    public void sendUpdatedEvent(TaskEventEntity event) {
        processor.updated().send(createMessage(event));
    }

    @Override
    public void sendChangedEmployeeEvent(TaskEventEntity event) {
        processor.changedEmployee().send(createMessage(event));
    }

}
