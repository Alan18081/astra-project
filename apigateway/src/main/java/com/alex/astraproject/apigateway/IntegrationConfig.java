package com.alex.astraproject.apigateway;

import com.alex.astraproject.apigateway.dispatchers.EmployeeProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@EnableBinding({ Processor.class, EmployeeProcessor.class })
public class IntegrationConfig {
    private static final String ENRICH = "enrich";

    @Bean
    public IntegrationFlow headerEnricherFlow() {
        return IntegrationFlows.from(ENRICH)
                .enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
                .channel(EmployeeProcessor.FIND_MANY_EMPLOYEES_BY_COMPANY)
                .get();
    }

    @ServiceActivator(inputChannel = EmployeeProcessor.FIND_MANY_EMPLOYEES_BY_COMPANY)
    @SendTo(EmployeeProcessor.RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
    public Message<?> handle(Message<?> message) {
        return MessageBuilder.withPayload(message.getPayload())
                .copyHeaders(message.getHeaders())
                .build();
    }

//    @StreamListener(EmployeeProcessor.FIND_MANY_EMPLOYEES_BY_COMPANY)
//    @SendTo(EmployeeProcessor.RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
//    public Message<?> process(Message<String> request) {
//        System.out.println(request.getPayload());
//        return MessageBuilder.withPayload(request.getPayload().toUpperCase())
//                .copyHeaders(request.getHeaders())
//                .build();
//    }

}
