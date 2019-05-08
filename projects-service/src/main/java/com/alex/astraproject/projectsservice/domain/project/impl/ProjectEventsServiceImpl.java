package com.alex.astraproject.projectsservice.domain.project.impl;

import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventEntity;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventsRepository;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventsService;
import com.alex.astraproject.projectsservice.domain.project.commands.common.*;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddParticipantCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveParticipantCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
public class ProjectEventsServiceImpl implements ProjectEventsService {

	@Autowired
	private ProjectEventsRepository projectEventsRepository;

	@Autowired
	private ProjectClient projectClient;

	@Override
	public Mono<ProjectEventEntity> createOne(CreateProjectCommand command) {
			String entityId = UUID.randomUUID().toString();
			ProjectEventEntity event = ProjectEventEntity.builder()
				.id(null)
				.projectId(entityId)
				.type(ProjectEventType.CREATED)
				.data(command)
				.revision(1)
				.timestamp(new Date().getTime())
				.build();
			return projectEventsRepository.save(event);
	}

	@Override
	public Mono<ProjectEventEntity> updateOne(UpdateProjectCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> buildEvent(project, ProjectEventType.UPDATED, command));
	}

	@Override
	public Mono<ProjectEventEntity> deleteOne(DeleteProjectCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> buildEvent(project, ProjectEventType.DELETED, command));

	}

	private Mono<ProjectEventEntity> buildEvent(Project project, String eventType, Object command) {
		return projectEventsRepository.findFirstByProjectIdOrderByRevisionDesc(project.getId())
			.flatMap(projectEventEntity -> {
				ProjectEventEntity event = ProjectEventEntity.builder()
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

	@Override
	public Mono<ProjectEventEntity> completeOne(CompleteProjectCommand command) {
		return null;
	}

	@Override
	public Mono<ProjectEventEntity> stopOne(StopProjectCommand command) {
		return null;
	}

	@Override
	public Mono<ProjectEventEntity> resumeOne(ResumeProjectCommand command) {
		return null;
	}

	@Override
	public Mono<ProjectEventEntity> addParticipant(AddParticipantCommand command) {
		return null;
	}

	@Override
	public Mono<ProjectEventEntity> removeParticipant(RemoveParticipantCommand command) {
		return null;
	}

	@Override
	public Flux<ProjectEventEntity> getEvents(GetEventsDto dto) {
		return null;
	}
}
