package com.alex.astraproject.companiesservice;

import com.alex.astraproject.shared.EnableSharedModule;
import com.alex.astraproject.shared.services.PasswordService;
import com.alex.astraproject.shared.services.impl.PasswordServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSharedModule
@EnableReactiveMongoRepositories
@RibbonClient(name = "companies-service")
public class CompaniesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompaniesServiceApplication.class, args);
    }

		@Bean
		@LoadBalanced
		public WebClient.Builder webClientBuilder() {
    	return WebClient.builder();
		}

		@Bean
		public PasswordService passwordService() {
    	return new PasswordServiceImpl();
		}
}
