package com.alex.astraproject.companiesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CompaniesServiceApplication {

    public static void main(String[] args) {
        System.out.println("Some stuff");
        SpringApplication.run(CompaniesServiceApplication.class, args);
    }

}
