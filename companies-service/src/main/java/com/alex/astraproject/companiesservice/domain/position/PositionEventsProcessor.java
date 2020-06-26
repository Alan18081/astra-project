package com.alex.astraproject.companiesservice.domain.position;

import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.eventTypes.PositionEventType;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PositionEventsProcessor {

    @Output(PositionEventType.CREATED)
    MessageChannel created();

    @Output(PositionEventType.UPDATED)
    MessageChannel updated();

    @Output(PositionEventType.DELETED)
    MessageChannel fired();

}
