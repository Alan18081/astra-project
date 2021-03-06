package com.alex.astraproject.projectsservice.domain.sprint.impl;

import com.alex.astraproject.projectsservice.domain.sprint.CustomSprintEventsRepository;
import com.alex.astraproject.projectsservice.domain.sprint.SprintEventEntity;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.services.EventsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomSprintEventsRepositoryImpl implements CustomSprintEventsRepository {

	@Autowired
	private EventsQueryBuilder eventsQueryBuilder;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<SprintEventEntity> findManyEvents(GetEventsDto dto) {
		return mongoTemplate.find(eventsQueryBuilder.createQuery("sprintId", dto), SprintEventEntity.class);
	}
}
