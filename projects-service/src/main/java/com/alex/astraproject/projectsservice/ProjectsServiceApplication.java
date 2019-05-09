package com.alex.astraproject.projectsservice;

import com.alex.astraproject.shared.EnableSharedModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSharedModule
@EnableReactiveMongoRepositories
@RibbonClient(name = "projects-service")
public class ProjectsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectsServiceApplication.class, args);
    }

}
