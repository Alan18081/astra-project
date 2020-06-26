package com.alex.astraproject.queryservice.clients;

import com.alex.astraproject.shared.events.CompanyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CompanyClient {

	@Autowired
	private WebClient.Builder client;

	public Mono<CompanyEvent> findCompanyEventsById(UUID id, long revision) {
		return client.build()
			.get()
			.uri("http://query-service/companies/{id}/events?revisionFrom={revision}", id, revision)
			.retrieve()
			.bodyToMono(CompanyEvent.class);
	}
}
