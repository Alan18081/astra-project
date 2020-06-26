package com.alex.astraproject.queryservice.domain.company.impl;

import com.alex.astraproject.queryservice.domain.company.CompanyEntity;
import com.alex.astraproject.queryservice.domain.company.CompanyRepository;
import com.alex.astraproject.queryservice.domain.company.CompanyService;
import com.alex.astraproject.queryservice.domain.company.dto.FindManyCompaniesDto;
import com.alex.astraproject.queryservice.domain.company.dto.FindManyCompaniesWhereEmployeeWorkedDto;
import com.alex.astraproject.shared.events.CompanyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class CompanyServiceIml implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

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
        companyEntityMono.subscribe(companyEntity -> {
            companyEntity.applyEvent(event);
            companyRepository.save(companyEntity);
        });
    }

    public Mono<CompanyEntity> findOneByEmail(String email) {
        return Mono.justOrEmpty(companyRepository.findFirstByEmail(email));
    }

    @Override
    public Flux<CompanyEntity> findMany(FindManyCompaniesDto dto) {
        return null;
    }

    @Override
    public Flux<CompanyEntity> findManyWhereEmployeeWorked(FindManyCompaniesWhereEmployeeWorkedDto dto) {
        return null;
    }

    @Override
    public Mono<CompanyEntity> findById(String id) {
        return Mono.justOrEmpty(companyRepository.findById(id));
    }
}
