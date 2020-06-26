package com.alex.astraproject.projectsservice.clients;

import com.alex.astraproject.shared.entities.Task;
import reactor.core.publisher.Mono;

public interface TaskClient {

	Mono<Task> findTaskById(String id);

}
