package com.alex.astraproject.queryservice.domain.company;

import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.events.CompanyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@Slf4j
@EnableBinding(CompanyProcessor.class)
public class CompanyHandler {

    @Autowired
    private CompanyService companyService;

    @StreamListener(CompanyEventType.CREATED)
    public void onCompanyCreated(@Payload CompanyEvent companyEvent) {
        companyService.createOne(companyEvent);
    }

    @StreamListener(CompanyEventType.UPDATED)
    public void onCompanyUpdated(@Payload CompanyEvent companyEvent) {
        companyService.updateById(companyEvent);
    }

    @StreamListener(CompanyEventType.DELETED)
    public void onCompanyDeleted(@Payload CompanyEvent companyEvent) {
        companyService.updateById(companyEvent);
    }
}
