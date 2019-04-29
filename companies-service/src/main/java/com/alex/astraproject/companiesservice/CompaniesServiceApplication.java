package com.alex.astraproject.companiesservice;

import com.alex.astraproject.shared.EnableSharedModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.client.WebClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveFeignClients
@EnableFeignClients
@EnableSharedModule
@EnableReactiveMongoRepositories
@RibbonClient(name = "companies-service")
public class CompaniesServiceApplication {
    public static void main(String[] args) {
        System.out.println("Some stuff");
        SpringApplication.run(CompaniesServiceApplication.class, args);
    }

		@Bean
		@LoadBalanced
		public WebClient.Builder webClientBuilder() {
    	return WebClient.builder();
		}
}
