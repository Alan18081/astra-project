package com.alex.astraproject.queryservice.domain.position;


import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.events.PositionEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PositionService extends Mutable<PositionEvent> {

	Mono<PositionEntity> findById(String id);

	Flux<PositionEntity> findMany();

	Mono<PositionEntity> findOneByName(String name);

}
