package com.alex.astraproject.companiesservice.domain.position.impl;

import com.alex.astraproject.companiesservice.domain.position.CustomPositionEventsRepository;
import com.alex.astraproject.companiesservice.domain.position.PositionEventEntity;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.services.EventsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomPositionEventsRepositoryImpl implements CustomPositionEventsRepository {

	@Autowired
	private EventsQueryBuilder eventsQueryBuilder;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<PositionEventEntity> findManyEvents(GetEventsDto dto) {
		return mongoTemplate.find(eventsQueryBuilder.createQuery("positionId", dto), PositionEventEntity.class);
	}
}
