package com.alex.astraproject.companiesservice.domain.employee.impl;

import com.alex.astraproject.companiesservice.domain.employee.CustomEmployeeEventsRepository;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.services.EventsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomEmployeeEventsRepositoryImpl implements CustomEmployeeEventsRepository {

	@Autowired
	private EventsQueryBuilder eventsQueryBuilder;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<EmployeeEventEntity> findManyEvents(GetEventsDto dto) {
		return mongoTemplate.find(eventsQueryBuilder.createQuery("employeeId", dto), EmployeeEventEntity.class);
	}
}
