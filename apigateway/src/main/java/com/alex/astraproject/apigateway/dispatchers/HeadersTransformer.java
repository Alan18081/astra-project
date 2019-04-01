package com.alex.astraproject.apigateway.dispatchers;

import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class HeadersTransformer {

    @Transformer
    public Message<?> transform(Message<?> message) {
        return MessageBuilder.withPayload(message.getHeaders())
                .copyHeaders(message.getHeaders())
                .build();
    }

}
