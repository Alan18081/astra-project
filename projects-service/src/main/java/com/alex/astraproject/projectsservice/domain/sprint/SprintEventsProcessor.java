package com.alex.astraproject.projectsservice.domain.sprint;

import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.eventTypes.SprintEventType;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SprintEventsProcessor {

    @Output(SprintEventType.CREATED)
    MessageChannel created();

    @Output(SprintEventType.UPDATED)
    MessageChannel updated();

    @Output(SprintEventType.DELETED)
    MessageChannel deleted();

    @Output(SprintEventType.COMPLETED)
    MessageChannel completed();

    @Output(SprintEventType.CREATED_TASK_STATUS)
    MessageChannel createdTaskStatus();

    @Output(SprintEventType.DELETED_TASK_STATUS)
    MessageChannel deletedTaskStatus();
}
