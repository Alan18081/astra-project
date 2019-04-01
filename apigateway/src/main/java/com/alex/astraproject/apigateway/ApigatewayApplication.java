package com.alex.astraproject.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

}
