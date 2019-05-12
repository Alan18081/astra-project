package com.alex.astraproject.queryservice.domain.project;

import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProjectProcessor {

    @Input(ProjectEventType.CREATED)
    SubscribableChannel created();

    @Input(ProjectEventType.UPDATED)
    SubscribableChannel updated();

}
