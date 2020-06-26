package com.alex.astraproject.queryservice.clients;

import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.events.EmployeeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class EmployeeClient {
	private static final String BASE_URL = "http://query-service/employees";

	@Autowired
	private WebClient.Builder client;

	public Mono<EmployeeEvent> findEmployeeEventsById(String id, long revision) {
		return client.build()
			.get()
			.uri(BASE_URL + "/{id}/events?revisionFrom={revision}", id, revision)
			.retrieve()
			.bodyToMono(EmployeeEvent.class);
	}
}
