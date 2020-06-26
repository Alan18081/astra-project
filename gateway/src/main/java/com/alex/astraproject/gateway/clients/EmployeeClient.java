package com.alex.astraproject.gateway.clients;

import com.alex.astraproject.shared.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EmployeeClient {

	@Autowired
	@Qualifier("webClient")
	private WebClient.Builder client;

	public Mono<Employee> findEmployeeByEmail(String email) {
		return client.build().get().uri("lb://query-service/employees/email/{email}", email)
			.exchange()
			.flatMap(clientResponse -> {
				return clientResponse.bodyToMono(Employee.class);
			});
	}
}
