package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class EmployeeClient {

	@Autowired
	private WebClient.Builder client;

	public Mono<Boolean> isEmployeeExists(String email) {
		return client.build().get().uri("http://query-service/employees/email/{email}", email)
			.exchange()
			.flatMap(clientResponse -> {
				if(clientResponse.rawStatusCode() == 404) {
					return Mono.just(false);
				}
				return Mono.just(true);
			});
	}

	public Mono<Employee> findEmployeeById(UUID id) {
		return client.build().get().uri("http://query-service/employees/{id}", id)
			.retrieve()
			.bodyToMono(Employee.class);
	}
}
