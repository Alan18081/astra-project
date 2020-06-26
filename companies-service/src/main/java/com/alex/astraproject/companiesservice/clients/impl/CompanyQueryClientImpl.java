package com.alex.astraproject.companiesservice.clients.impl;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.shared.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CompanyQueryClientImpl implements CompanyQueryClient {
	private static final String BASE_URL = "http://query-service/companies";

	@Autowired
	private WebClient.Builder client;

	public Mono<Boolean> isCompanyExistsByEmail(String email) {
		return client.build().get().uri(BASE_URL + "/email/{email}", email)
			.exchange()
			.flatMap(clientResponse -> {
				if(clientResponse.rawStatusCode() == 404) {
					return Mono.just(false);
				}
				return Mono.just(true);
			});
	}

	public Mono<Company> findCompanyById(String id) {
		return client.build().get().uri(BASE_URL + "/{id}", id)
			.exchange()
			.flatMap(clientResponse -> clientResponse.bodyToMono(Company.class));
	}
}
