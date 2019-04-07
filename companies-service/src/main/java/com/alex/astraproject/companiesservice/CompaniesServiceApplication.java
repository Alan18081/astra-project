package com.alex.astraproject.companiesservice;

import com.alex.astraproject.shared.EnableSharedModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSharedModule
@EnableMongoRepositories(basePackages = "com.alex.astraproject.companiesservice")
public class CompaniesServiceApplication {

    public static void main(String[] args) {
        System.out.println("Some stuff");
        SpringApplication.run(CompaniesServiceApplication.class, args);
    }

}
