package com.alex.astraproject.projectsservice.clients.impl;

import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.shared.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProjectClientImpl implements ProjectClient {

	@Autowired
	private WebClient.Builder client;

	public Mono<Project> findProjectById(String id) {
		return client.build().get().uri("http://query-service/projects/{id}", id)
			.exchange()
			.flatMap(clientResponse -> clientResponse.bodyToMono(Project.class));
	}

	public Mono<Project> findProjectById(String id, boolean includeEmployees) {
		return client.build().get().uri("http://query-service/projects/{id}?includeEmployees={includeEmployees}", id, includeEmployees)
			.exchange()
			.flatMap(clientResponse -> clientResponse.bodyToMono(Project.class));
	}
}
