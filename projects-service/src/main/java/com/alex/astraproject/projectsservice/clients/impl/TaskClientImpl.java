package com.alex.astraproject.projectsservice.clients.impl;

import com.alex.astraproject.projectsservice.clients.TaskClient;
import com.alex.astraproject.shared.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TaskClientImpl implements TaskClient {
	private static final String BASE_URL = "http://query-service/tasks";

	@Autowired
	private WebClient.Builder client;

	@Override
	public Mono<Task> findTaskById(String id) {
		return client.build().get().uri(BASE_URL + "/{id}", id)
			.exchange()
			.flatMap(clientResponse -> clientResponse.bodyToMono(Task.class));
	}

}
