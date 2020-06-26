package com.alex.astraproject.queryservice.domain.employee;


import com.alex.astraproject.queryservice.domain.employee.dto.FindManyEmployeesDto;
import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService extends Mutable<EmployeeEvent> {

	Mono<EmployeeEntity> findById(String id);

	Mono<EmployeeEntity> findOneByEmail(String email);

	Flux<EmployeeEntity> findMany(FindManyEmployeesDto dto);

	Flux<EmployeeEntity> findManyThatWorksWithProvidedEmployee(String employeeId);

}
