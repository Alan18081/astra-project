package com.alex.astraproject.projectsservice.clients;

import com.alex.astraproject.shared.entities.Company;
import reactor.core.publisher.Mono;

public interface CompanyClient {

	Mono<Company> findCompanyById(String id);

}
