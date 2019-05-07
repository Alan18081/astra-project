package com.alex.astraproject.companiesservice.domain.company;

import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

public interface CustomCompanyEventsRepository {

	Flux<CompanyEventEntity> findManyEvents(String id, Long revisionFrom, Long revisionTo);

}
