package com.alex.astraproject.apigateway;

import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class JsonMessageConverter extends AbstractMessageConverter {
    public JsonMessageConverter() {
        super(new MimeType("application", "json"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return true;
    }
}
