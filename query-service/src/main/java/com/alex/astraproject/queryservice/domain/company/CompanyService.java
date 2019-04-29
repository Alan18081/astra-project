package com.alex.astraproject.queryservice.domain.company;

import com.alex.astraproject.queryservice.clients.CompanyClient;
import com.alex.astraproject.shared.events.CompanyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyClient companyClient;

    public Flux<CompanyEntity> findMany() {
        return Flux.fromIterable(companyRepository.findAll());
    }

    public void createCompany(CompanyEvent event) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.initialize();
        companyEntity.replay(Arrays.asList(event));
        companyRepository.save(companyEntity);
    }

    public void updateCompany(CompanyEvent event) {
        Mono<CompanyEntity> companyEntityMono = Mono.justOrEmpty(companyRepository.findById(event.getCompanyId()));
        companyEntityMono
            .subscribe(companyEntity -> companyClient
              .findManyEvents(companyEntity.getId().toString(), companyEntity.getRevision())
              .doOnNext(companyEntity::applyEvent)
              .thenMany(other -> companyRepository.save(companyEntity))
            );
    }

    public Mono<CompanyEntity> findOneById(UUID id) {
        return Mono.justOrEmpty(companyRepository.findById(id));
    }

    public Mono<CompanyEntity> findOneByEmail(String email) {
        return Mono.justOrEmpty(companyRepository.findFirstByEmail(email));
    }
}
