package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.messaging.MessageChannel;

public interface CompanyEventsProcessor {

    @Output(CompanyEventType.CREATED)
    MessageChannel created();

    @Output(CompanyEventType.UPDATED)
    MessageChannel updated();

    @Output(CompanyEventType.DELETED)
    MessageChannel deleted();

}
