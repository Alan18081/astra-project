package com.alex.astraproject.projectsservice.domain.project;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProjectEventsRepository extends ReactiveMongoRepository<ProjectEventEntity, String>, CustomProjectEventsRepository {

	Mono<ProjectEventEntity> findFirstByProjectIdOrderByRevisionDesc(
		String projectId
	);

}
