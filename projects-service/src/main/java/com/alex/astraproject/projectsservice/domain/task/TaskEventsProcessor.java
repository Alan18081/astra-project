package com.alex.astraproject.projectsservice.domain.task;

import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.eventTypes.TaskEventType;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TaskEventsProcessor {

    @Output(TaskEventType.CREATED)
    MessageChannel created();

    @Output(TaskEventType.UPDATED)
    MessageChannel updated();

    @Output(TaskEventType.DELETED)
    MessageChannel deleted();

    @Output(TaskEventType.CHANGED_EMPLOYEE)
    MessageChannel changedEmployee();

    @Output(TaskEventType.CHANGED_TASK_STATUS)
    MessageChannel changedTaskStatus();
}
