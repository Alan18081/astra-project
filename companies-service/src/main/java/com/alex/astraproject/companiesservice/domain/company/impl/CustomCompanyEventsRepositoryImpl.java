package com.alex.astraproject.companiesservice.domain.company.impl;

import com.alex.astraproject.companiesservice.domain.company.CompanyEventEntity;
import com.alex.astraproject.companiesservice.domain.company.CustomCompanyEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomCompanyEventsRepositoryImpl implements CustomCompanyEventsRepository {

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<CompanyEventEntity> findManyEvents(String id, Long revisionFrom, Long revisionTo) {
		final Query query = new Query();
//		query.addCriteria(Criteria.where("companyId").is(id));
		Criteria revisionCriteria = Criteria.where("revision");
		if(revisionFrom != null) {
			revisionCriteria = revisionCriteria.gt(revisionFrom);
		}

		if(revisionTo != null) {
			revisionCriteria = revisionCriteria.lt(revisionTo);
		}
		query.addCriteria(revisionCriteria);
		System.out.println(query.toString());
		return mongoTemplate.find(query, CompanyEventEntity.class);
	}
}
