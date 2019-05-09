package com.alex.astraproject.projectsservice.clients.impl;

import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.shared.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class EmployeeClientImpl implements EmployeeClient {
	private final String BASE_URL = "http://query-service/employees";

	@Autowired
	private WebClient.Builder client;

	public Mono<Employee> findEmployeeByIdAndCompanyId(String employeeId, String companyId) {
		return client.build().get().uri(BASE_URL + "/{id}?companyId={companyId}", employeeId, companyId)
			.retrieve()
			.bodyToMono(Employee.class);
	}
}
