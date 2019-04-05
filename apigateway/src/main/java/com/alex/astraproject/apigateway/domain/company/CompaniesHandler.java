package com.alex.astraproject.apigateway.domain.company;

import com.alex.astraproject.shared.Events;
import com.alex.astraproject.shared.events.CompanyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
@EnableBinding(CompaniesProcessor.class)
public class CompaniesHandler {

    @StreamListener(Events.COMPANY_CREATED)
    public void companyCreated(Message<CompanyEvent> companyEventMessage) {

    }

}
