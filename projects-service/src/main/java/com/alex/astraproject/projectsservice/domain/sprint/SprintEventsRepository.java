package com.alex.astraproject.projectsservice.domain.sprint;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SprintEventsRepository extends ReactiveMongoRepository<SprintEventEntity, String>, CustomSprintEventsRepository {

	Mono<SprintEventEntity> findFirstBySprintIdOrderByRevisionDesc(
		String projectId
	);

}
