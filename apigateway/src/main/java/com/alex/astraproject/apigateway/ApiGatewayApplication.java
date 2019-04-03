package com.alex.astraproject.apigateway;

import com.alex.astraproject.shared.EnableSharedModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.alex.astraproject.apigateway")
@EnableDiscoveryClient
@EnableSharedModule
@RibbonClient(name = "api-gateway")
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
