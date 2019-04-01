package com.alex.astraproject.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.converter.MessageConverter;

@SpringBootApplication
public class ApigatewayApplication {

//    @Bean
//    public MessageConverter messageConverter() {
//        return new JsonMessageConverter();
//    }

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

}
