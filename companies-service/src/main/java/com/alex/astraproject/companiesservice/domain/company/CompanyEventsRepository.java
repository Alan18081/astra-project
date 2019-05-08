package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.shared.interfaces.EventsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface CompanyEventsRepository extends
	ReactiveMongoRepository<CompanyEventEntity, String>,
	CustomCompanyEventsRepository
{

  Mono<CompanyEventEntity> findFirstByCompanyIdOrderByRevisionDesc(
  	String companyId
  );

	Flux<CompanyEventEntity> findAllByCompanyIdAndRevisionGreaterThan(
		String companyId, long revisionFrom, Pageable pageable
	);

}
