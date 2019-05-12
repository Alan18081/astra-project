package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CompanyQueryClient {

	Mono<Boolean> isCompanyExistsByEmail(String email);

	Mono<Company> findCompanyById(String id);
}
