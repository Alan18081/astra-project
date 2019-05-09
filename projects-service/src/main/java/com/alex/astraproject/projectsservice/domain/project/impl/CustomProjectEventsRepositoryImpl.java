package com.alex.astraproject.projectsservice.domain.project.impl;

import com.alex.astraproject.projectsservice.domain.project.CustomProjectEventsRepository;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventEntity;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.services.EventsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomProjectEventsRepositoryImpl implements CustomProjectEventsRepository {

	@Autowired
	private EventsQueryBuilder eventsQueryBuilder;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<ProjectEventEntity> findManyEvents(GetEventsDto dto) {
		return mongoTemplate.find(eventsQueryBuilder.createQuery("projectId", dto), ProjectEventEntity.class);
	}
}
