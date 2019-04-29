package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CompanyEventsRepository extends ReactiveMongoRepository<CompanyEventEntity, UUID> {

    Mono<EmployeeEventEntity> findFirstByCompanyIdOrderByRevisionDesc(UUID companyId);

    Flux<CompanyEventEntity> findAllByCompanyIdAndRevisionGreaterThan(UUID companyId, int revision);

}
