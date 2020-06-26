package com.alex.astraproject.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class Router {

	@Bean
	public RouteLocator builder(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(r -> r.method(HttpMethod.POST).uri("lb://companies-service"))
			.build();
	}

}
