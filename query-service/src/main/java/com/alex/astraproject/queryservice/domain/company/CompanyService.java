package com.alex.astraproject.queryservice.domain.company;


import com.alex.astraproject.shared.dto.companies.FindCompanyByIdDto;
import com.alex.astraproject.shared.events.CompanyEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CompanyService extends Mutable<CompanyEvent> {

	Mono<CompanyEntity> findById(String id);

	Mono<CompanyEntity> findOneByEmail(String email);

	Flux<CompanyEntity> findMany();

}
