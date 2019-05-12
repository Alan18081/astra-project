package com.alex.astraproject.projectsservice.domain.sprint.impl;

import com.alex.astraproject.projectsservice.clients.CompanyClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.domain.sprint.SprintEventEntity;
import com.alex.astraproject.projectsservice.domain.sprint.SprintEventsRepository;
import com.alex.astraproject.projectsservice.domain.sprint.SprintService;
import com.alex.astraproject.projectsservice.domain.sprint.commands.CompleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.CreateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.DeleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.UpdateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.*;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveDesiredStatusException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.statuses.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
public class SprintServiceImpl implements SprintService {

	@Autowired
	private SprintEventsRepository projectEventsRepository;

	@Autowired
	private ProjectClient projectClient;

	@Autowired
	private CompanyClient companyClient;

	@Override
	public Mono<SprintEventEntity> createOne(CreateSprintCommand command) {
		return companyClient.findCompanyById(command.getCompanyId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)))
			.flatMap(company -> {
				String entityId = UUID.randomUUID().toString();
				SprintEventEntity event = SprintEventEntity.builder()
					.id(null)
					.projectId(entityId)
					.type(ProjectEventType.CREATED)
					.data(command)
					.revision(1)
					.timestamp(new Date().getTime())
					.build();
				return projectEventsRepository.save(event);
			});
	}

	@Override
	public Mono<SprintEventEntity> updateOne(UpdateSprintCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> buildEvent(project, ProjectEventType.UPDATED, command));
	}

	@Override
	public Mono<SprintEventEntity> deleteOne(DeleteSprintCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> buildEvent(project, ProjectEventType.DELETED, command));

	}

	@Override
	public Mono<SprintEventEntity> completeOne(CompleteSprintCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				if(project.getStatus() == ProjectStatus.COMPLETED) {
					return Mono.error(new ProjectAlreadyHaveDesiredStatusException(Errors.PROJECT_ALREADY_COMPLETED));
				}
				return buildEvent(project, ProjectEventType.COMPLETED, command);
			});
	}

	@Override
	public Flux<SprintEventEntity> getEvents(GetEventsDto dto) {
		return projectEventsRepository.findManyEvents(dto);
	}

	private Mono<SprintEventEntity> buildEvent(Project project, String eventType, Object command) {
		return projectEventsRepository.findFirstByProjectIdOrderByRevisionDesc(project.getId())
			.flatMap(projectEventEntity -> {
				SprintEventEntity event = SprintEventEntity.builder()
					.id(null)
					.projectId(project.getId())
					.type(eventType)
					.data(command)
					.revision(projectEventEntity.getRevision() + 1)
					.timestamp(new Date().getTime())
					.build();
				return projectEventsRepository.save(event);
			});
	}
}
