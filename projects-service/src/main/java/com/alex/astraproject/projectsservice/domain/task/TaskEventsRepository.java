package com.alex.astraproject.projectsservice.domain.task;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TaskEventsRepository extends ReactiveMongoRepository<TaskEventEntity, String>, CustomTaskEventsRepository {

	Mono<TaskEventEntity> findFirstByTaskIdOrderByRevisionDesc(
		String projectId
	);

}
