package com.alex.astraproject.queryservice.domain.company.impl;

import com.alex.astraproject.queryservice.clients.CompanyClient;
import com.alex.astraproject.queryservice.domain.company.CompanyEntity;
import com.alex.astraproject.queryservice.domain.company.CompanyRepository;
import com.alex.astraproject.queryservice.domain.company.CompanyService;
import com.alex.astraproject.shared.dto.companies.FindCompanyByIdDto;
import com.alex.astraproject.shared.events.CompanyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Service
public class CompanyServiceIml implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyClient companyClient;

    public Flux<CompanyEntity> findMany() {
        return Flux.fromIterable(companyRepository.findAll());
    }

    @Override
    public void createOne(CompanyEvent event) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.replay(Arrays.asList(event));
        companyRepository.save(companyEntity);
    }

    @Override
    public void updateById(CompanyEvent event) {
        Mono<CompanyEntity> companyEntityMono = Mono.justOrEmpty(companyRepository.findById(event.getCompanyId()));
        applyEvents(companyEntityMono);
    }

    @Override
    public void deleteById(CompanyEvent event) {
        Mono<CompanyEntity> companyEntityMono = Mono.justOrEmpty(companyRepository.findById(event.getCompanyId()));
        applyEvents(companyEntityMono);
    }

    public Mono<CompanyEntity> findOneByEmail(String email) {
        return Mono.justOrEmpty(companyRepository.findFirstByEmail(email));
    }

    @Override
    public Mono<CompanyEntity> findById(String id) {
        return Mono.justOrEmpty(companyRepository.findById(id));
    }

    private void applyEvents(Mono<CompanyEntity> companyEntityMono) {
        companyEntityMono
          .subscribe(companyEntity -> companyClient
            .findCompanyEventsById(UUID.fromString(companyEntity.getId()), companyEntity.getRevision())
            .doOnNext(companyEntity::applyEvent)
            .thenMany(other -> companyRepository.save(companyEntity))
          );
    }
}
