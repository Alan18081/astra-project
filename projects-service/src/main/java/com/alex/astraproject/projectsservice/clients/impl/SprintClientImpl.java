package com.alex.astraproject.projectsservice.clients.impl;

import com.alex.astraproject.projectsservice.clients.SprintClient;
import com.alex.astraproject.shared.entities.Sprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SprintClientImpl implements SprintClient {
	private static final String BASE_URL = "http://query-service/sprints";

	@Autowired
	private WebClient.Builder client;

	@Override
	public Mono<Sprint> findSprintById(String id) {
		return client.build().get().uri(BASE_URL + "/{id}", id)
			.exchange()
			.flatMap(clientResponse -> clientResponse.bodyToMono(Sprint.class));
	}

}
