package com.alex.astraproject.queryservice.domain.sprint;

import com.alex.astraproject.shared.events.SprintEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SprintService extends Mutable<SprintEvent> {

	Mono<SprintEntity> findOneById(String id);

	Flux<SprintEntity> findMany();

}
