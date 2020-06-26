package com.alex.astraproject.queryservice.domain.company;


import com.alex.astraproject.queryservice.domain.company.dto.FindManyCompaniesDto;
import com.alex.astraproject.queryservice.domain.company.dto.FindManyCompaniesWhereEmployeeWorkedDto;
import com.alex.astraproject.shared.events.CompanyEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyService extends Mutable<CompanyEvent> {

	Mono<CompanyEntity> findById(String id);

	Mono<CompanyEntity> findOneByEmail(String email);

	Flux<CompanyEntity> findMany(FindManyCompaniesDto dto);

	Flux<CompanyEntity> findManyWhereEmployeeWorked(FindManyCompaniesWhereEmployeeWorkedDto dto);

}
