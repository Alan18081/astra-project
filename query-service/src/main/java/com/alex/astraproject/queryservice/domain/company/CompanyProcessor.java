package com.alex.astraproject.queryservice.domain.company;

import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CompanyProcessor {

    @Input(CompanyEventType.CREATED)
    SubscribableChannel created();

    @Input(CompanyEventType.UPDATED)
    SubscribableChannel updated();

}
