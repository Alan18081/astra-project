package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.shared.interfaces.EventsRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface EmployeeEventsRepository extends ReactiveMongoRepository<EmployeeEventEntity, String> {

    Mono<EmployeeEventEntity> findFirstByEmployeeIdOrderByRevisionDesc(UUID employeeId);

    Flux<EmployeeEventEntity> findAllByEmployeeIdAndRevisionGreaterThan(UUID employeeId, int revision);

}
