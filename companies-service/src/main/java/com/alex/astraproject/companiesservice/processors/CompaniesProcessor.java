package com.alex.astraproject.companiesservice.processors;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CompaniesProcessor {

    String CREATE_COMPANY = "[CompaniesService] Create Company";
    String CREATED_COMPANY = "[CompaniesService] Created Company";

    @Input(CREATE_COMPANY)
    SubscribableChannel createCompany();

    @Output(CREATED_COMPANY)
    MessageChannel createdCompany();

}
