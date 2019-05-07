package com.alex.astraproject.companiesservice.domain.company;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface CompanyEventsRepository extends
	ReactiveMongoRepository<CompanyEventEntity, String>,
	CustomCompanyEventsRepository
{
	Flux<CompanyEventEntity> findAllByCompanyId(
		String companyId, Pageable pageable
	);

  Mono<CompanyEventEntity> findFirstByCompanyIdOrderByRevisionDesc(
  	String companyId
  );

	Flux<CompanyEventEntity> findAllByCompanyIdAndRevisionGreaterThan(
		String companyId, long revisionFrom, Pageable pageable
	);


  @Query("{ 'revision': { $gt: ?0, $lt: ?1 }}")
  Flux<CompanyEventEntity> findAllEvents(Long revisionFrom, Long revisionTo);
}
