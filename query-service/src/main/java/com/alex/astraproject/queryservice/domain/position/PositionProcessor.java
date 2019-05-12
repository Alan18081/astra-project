package com.alex.astraproject.queryservice.domain.position;

import com.alex.astraproject.shared.eventTypes.PositionEventType;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PositionProcessor {

    @Input(PositionEventType.CREATED)
    SubscribableChannel created();

    @Input(PositionEventType.UPDATED)
    SubscribableChannel updated();

}
