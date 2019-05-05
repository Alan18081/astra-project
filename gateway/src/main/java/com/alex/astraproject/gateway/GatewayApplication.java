package com.alex.astraproject.gateway;

import com.alex.astraproject.shared.services.PasswordService;
import com.alex.astraproject.shared.services.impl.PasswordServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder webClient() {
		return WebClient.builder();
	}

	@Bean
	public PasswordService passwordService() {
		return new PasswordServiceImpl();
	}
}
