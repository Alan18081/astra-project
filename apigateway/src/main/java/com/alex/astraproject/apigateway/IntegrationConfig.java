package com.alex.astraproject.apigateway;

import com.alex.astraproject.apigateway.dispatchers.EmployeeProcessor;
import com.alex.astraproject.apigateway.dispatchers.HeadersTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;

@Configuration
@EnableIntegration
@IntegrationComponentScan
@EnableBinding({ EmployeeProcessor.class, Processor.class })
public class IntegrationConfig {
    private static final String ENRICH = "enrich";

    @Bean
    public IntegrationFlow headerEnricherFlow() {
        return IntegrationFlows.from(ENRICH)
                .enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
                .channel(EmployeeProcessor.FIND_MANY_EMPLOYEES_BY_COMPANY)
                .get();
    }

    @Bean
    public IntegrationFlow responseFlow() {
        return IntegrationFlows.from(EmployeeProcessor.RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
                .channel(MessageChannels.direct("responseChannel"))
                .get();
    }

//    @StreamListener(EmployeeProcessor.RES_FIND_MANY_EMPLOYEES_BY_COMPANY)
//    public void sendTo(Message<?> message) {
//        System.out.println("Integration config");
//        System.out.println(message.getPayload());
//    }

//    @StreamListener(EmployeeProcessor.)
//    public Message<?> process(Message<String> request) {
//        System.out.println(request.getPayload());
//        return MessageBuilder.withPayload(request.getPayload().toUpperCase())
//                .copyHeaders(request.getHeaders())
//                .build();
//    }

}
