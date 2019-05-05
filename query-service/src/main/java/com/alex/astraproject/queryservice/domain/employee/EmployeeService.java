package com.alex.astraproject.queryservice.domain.employee;


import com.alex.astraproject.queryservice.domain.company.CompanyEntity;
import com.alex.astraproject.shared.events.CompanyEvent;
import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeeService extends Mutable<EmployeeEvent> {

	Mono<EmployeeEntity> findById(String id);

	Mono<EmployeeEntity> findOneByEmail(String email);

	Flux<EmployeeEntity> findMany();

}
