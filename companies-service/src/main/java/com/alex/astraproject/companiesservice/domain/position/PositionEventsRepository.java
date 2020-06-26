package com.alex.astraproject.companiesservice.domain.position;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PositionEventsRepository extends ReactiveMongoRepository<PositionEventEntity, String>, CustomPositionEventsRepository {

    Mono<PositionEventEntity> findFirstByPositionIdOrderByRevisionDesc(String positionId);

}
