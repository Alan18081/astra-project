package com.alex.astraproject.queryservice.domain.project;


import com.alex.astraproject.shared.events.ProjectEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService extends Mutable<ProjectEvent> {

	Mono<ProjectEntity> findById(String id);

	Flux<ProjectEntity> findMany();

}
