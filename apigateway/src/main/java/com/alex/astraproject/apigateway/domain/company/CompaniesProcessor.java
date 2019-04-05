package com.alex.astraproject.apigateway.domain.company;

import com.alex.astraproject.shared.Events;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CompaniesProcessor {

    @Input(Events.COMPANY_CREATED)
    SubscribableChannel companyCreated();

    @Input(Events.COMPANY_UPDATED)
    SubscribableChannel companyUpdated();

    @Input(Events.COMPANY_DELETED)
    SubscribableChannel companyDeleted();
}
