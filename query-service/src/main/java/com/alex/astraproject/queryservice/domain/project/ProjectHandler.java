package com.alex.astraproject.queryservice.domain.project;

import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.events.ProjectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@EnableBinding(ProjectProcessor.class)
public class ProjectHandler {

    @Autowired
    private ProjectService projectService;

    @StreamListener(ProjectEventType.CREATED)
    public void onCompanyCreated(@Payload ProjectEvent projectEvent) {
        projectService.createOne(projectEvent);
    }

    @StreamListener(ProjectEventType.UPDATED)
    public void onCompanyUpdated(@Payload ProjectEvent projectEvent) {
        projectService.updateById(projectEvent);
    }

}
