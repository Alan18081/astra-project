package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CompanyClient {

	@Autowired
	private WebClient.Builder client;

	public Mono<Boolean> isCompanyExistsByEmail(String email) {
		return client.build().get().uri("http://query-service/companies/email/{email}", email)
			.exchange()
			.flatMap(clientResponse -> {
				if(clientResponse.rawStatusCode() == 404) {
					return Mono.just(false);
				}
				return Mono.just(true);
			});
	}

	public Mono<Company> findCompanyById(UUID id) {
		return client.build().get().uri("http://query-service/companies/{id}", id)
			.retrieve()
			.bodyToMono(Company.class);
	}
}
