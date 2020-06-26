package com.alex.astraproject.projectsservice.clients;

import com.alex.astraproject.shared.entities.Employee;
import reactor.core.publisher.Mono;

public interface EmployeeClient {

	Mono<Employee> findEmployeeByIdAndCompanyId(String employeeId, String companyId);

	Mono<Employee> findEmployeeById(String id);

}
