package com.alex.astraproject.projectsservice.clients.impl;

import com.alex.astraproject.projectsservice.clients.CompanyClient;
import com.alex.astraproject.shared.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CompanyClientImpl implements CompanyClient {

	@Autowired
	private WebClient.Builder client;

	public Mono<Company> findCompanyById(String id) {
		return client.build().get().uri("http://query-service/companies/{id}", id)
			.exchange()
			.flatMap(clientResponse -> clientResponse.bodyToMono(Company.class));
	}
}
