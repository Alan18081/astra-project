package com.alex.astraproject.gateway.clients;

import com.alex.astraproject.shared.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CompanyClient {

	@Autowired
	private WebClient.Builder client;

	public Mono<Company> findCompanyByEmail(String email) {
		return client.build().get().uri("http://query-service/companies/email/{email}", email)
			.retrieve()
			.bodyToMono(Company.class);
	}
}
