package com.alex.astraproject.companiesservice.clients.impl;

import com.alex.astraproject.companiesservice.clients.PositionQueryClient;
import com.alex.astraproject.shared.entities.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class PositionQueryClientImpl implements PositionQueryClient {

	@Autowired
	private WebClient.Builder client;

	@Override
	public Mono<Position> findPositionById(String id) {
		return client.build().get().uri("/{id}", id)
			.retrieve()
			.bodyToMono(Position.class);
	}
}
