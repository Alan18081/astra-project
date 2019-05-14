package com.alex.astraproject.projectsservice.domain.sprint.impl;

import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.clients.SprintClient;
import com.alex.astraproject.projectsservice.domain.sprint.SprintEventEntity;
import com.alex.astraproject.projectsservice.domain.sprint.SprintEventsRepository;
import com.alex.astraproject.projectsservice.domain.sprint.SprintService;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.CompleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.CreateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.DeleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.UpdateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.statuses.CreateTaskStatusCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.statuses.DeleteTaskStatusCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Sprint;
import com.alex.astraproject.shared.entities.Status;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.eventTypes.SprintEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveDesiredStatusException;
import com.alex.astraproject.shared.exceptions.sprints.SprintAlreadyHasProvidedTaskStatus;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.statuses.SprintStatus;
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
	private SprintClient sprintClient;

	@Autowired
	private ProjectClient projectClient;

	@Override
	public Mono<SprintEventEntity> createOne(CreateSprintCommand command) {
		return projectClient.findProjectById(command.getCompanyId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(company -> {
				String entityId = UUID.randomUUID().toString();
				SprintEventEntity event = SprintEventEntity.builder()
					.id(null)
					.sprintId(entityId)
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
		return findSprint(command.getId())
			.flatMap(sprint -> buildEvent(sprint, SprintEventType.UPDATED, command));
	}

	@Override
	public Mono<SprintEventEntity> deleteOne(DeleteSprintCommand command) {
		return findSprint(command.getId())
			.flatMap(sprint -> buildEvent(sprint, SprintEventType.DELETED, command));

	}

	@Override
	public Mono<SprintEventEntity> createTaskStatus(CreateTaskStatusCommand command) {
		return findSprint(command.getSprintId())
			.flatMap(sprint -> {
				Status requiredStatus = new Status(command.getName());
				if(sprint.getTaskStatuses().contains(requiredStatus)) {
					return Mono.error(new SprintAlreadyHasProvidedTaskStatus());
				}
				return buildEvent(sprint, SprintEventType.CREATED_TASK_STATUS, command);
			});
	}

	@Override
	public Mono<SprintEventEntity> deleteTaskStatus(DeleteTaskStatusCommand command) {
		return findSprint(command.getSprintId())
			.flatMap(sprint -> {
				Status requiredStatus = new Status(command.getStatusName());
				if(!sprint.getTaskStatuses().contains(requiredStatus)) {
					return Mono.error(new SprintAlreadyHasProvidedTaskStatus());
				}
				return buildEvent(sprint, SprintEventType.DELETED_TASK_STATUS, command);
			});
	}

	@Override
	public Mono<SprintEventEntity> completeOne(CompleteSprintCommand command) {
		return findSprint(command.getId())
			.flatMap(sprint -> {
				if(sprint.getStatus() == SprintStatus.COMPLETED) {
					return Mono.error(new ProjectAlreadyHaveDesiredStatusException(Errors.SPRINT_ALREADY_COMPLETED));
				}
				return buildEvent(sprint, SprintEventType.COMPLETED, command);
			});
	}

	@Override
	public Flux<SprintEventEntity> getEvents(GetEventsDto dto) {
		return projectEventsRepository.findManyEvents(dto);
	}

	private Mono<Sprint> findSprint(String id) {
		return sprintClient.findSprintById(id)
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.SPRINT_NOT_FOUND_BY_ID)));
	}


	private Mono<SprintEventEntity> buildEvent(Sprint project, String eventType, Object command) {
		return projectEventsRepository.findFirstBySprintIdOrderByRevisionDesc(project.getId())
			.flatMap(projectEventEntity -> {
				SprintEventEntity event = SprintEventEntity.builder()
					.id(null)
					.sprintId(project.getId())
					.type(eventType)
					.data(command)
					.revision(projectEventEntity.getRevision() + 1)
					.timestamp(new Date().getTime())
					.build();
				return projectEventsRepository.save(event);
			});
	}
}
