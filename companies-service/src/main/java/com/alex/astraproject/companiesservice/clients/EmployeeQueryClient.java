package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Employee;
import reactor.core.publisher.Mono;

public interface EmployeeQueryClient {

	Mono<Boolean> isEmployeeExists(String email);

	Mono<Employee> findEmployeeById(String id);
}
