package com.alex.astraproject.queryservice.domain.position;

import com.alex.astraproject.shared.eventTypes.PositionEventType;
import com.alex.astraproject.shared.events.PositionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@EnableBinding(PositionProcessor.class)
public class PositionHandler {

    @Autowired
    private PositionService positionService;

    @StreamListener(PositionEventType.CREATED)
    public void onPositionCreated(@Payload PositionEvent positionEvent) {
        positionService.createOne(positionEvent);
    }

    @StreamListener(PositionEventType.UPDATED)
    public void onPositionUpdated(@Payload PositionEvent positionEvent) {
        positionService.updateById(positionEvent);
    }

}
