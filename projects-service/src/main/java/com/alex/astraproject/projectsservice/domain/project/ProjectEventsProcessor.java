package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProjectEventsProcessor {

    @Output(ProjectEventType.CREATED)
    MessageChannel created();

    @Output(ProjectEventType.UPDATED)
    MessageChannel updated();

    @Output(ProjectEventType.DELETED)
    MessageChannel deleted();

    @Output(ProjectEventType.STOPPED)
    MessageChannel stopped();

    @Output(ProjectEventType.RESUMED)
    MessageChannel resumed();

    @Output(ProjectEventType.ADDED_EMPLOYEE)
    MessageChannel addedEmployee();

    @Output(ProjectEventType.REMOVED_EMPLOYEE)
    MessageChannel removedEmployee();

    @Output(ProjectEventType.COMPLETED)
    MessageChannel completed();

    @Output(ProjectEventType.CHANGED_EMPLOYEE_POSITION)
    MessageChannel changedEmployeePosition();
}
