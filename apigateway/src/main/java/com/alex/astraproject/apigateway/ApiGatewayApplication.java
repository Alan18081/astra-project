package com.alex.astraproject.apigateway;

import com.alex.astraproject.shared.SharedContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients("com.alex.astraproject.apigateway")
@EnableDiscoveryClient
@ComponentScan("com.alex.astraproject.apigateway")
//@EnableSharedModule
@Import(SharedContext.class)
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
