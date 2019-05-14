package com.alex.astraproject.projectsservice.domain.task.impl;

import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.clients.SprintClient;
import com.alex.astraproject.projectsservice.clients.TaskClient;
import com.alex.astraproject.projectsservice.domain.task.TaskEventsRepository;
import com.alex.astraproject.projectsservice.domain.task.TaskEventEntity;
import com.alex.astraproject.projectsservice.domain.task.TaskService;
import com.alex.astraproject.projectsservice.domain.task.commands.*;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.entities.Task;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.eventTypes.TaskEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.projects.ProjectDoesNotHaveRequiredPositionException;
import com.alex.astraproject.shared.exceptions.tasks.TaskAlreadyAssignedToProvidedEmployee;
import com.alex.astraproject.shared.exceptions.tasks.TaskAlreadyHasProvidedStatus;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskEventsRepository taskEventsRepository;

	@Autowired
	private SprintClient sprintClient;

	@Autowired
	private TaskClient taskClient;

	@Autowired
	private ProjectClient projectClient;

	@Override
	public Mono<TaskEventEntity> createOne(CreateTaskCommand command) {
		return sprintClient.findSprintById(command.getSprintId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)))
			.flatMap(company -> {
				String entityId = UUID.randomUUID().toString();
				TaskEventEntity event = TaskEventEntity.builder()
					.id(null)
					.taskId(entityId)
					.type(ProjectEventType.CREATED)
					.data(command)
					.revision(1)
					.build();
				return taskEventsRepository.save(event);
			});
	}

	@Override
	public Mono<TaskEventEntity> updateOne(UpdateTaskCommand command) {
		return findTask(command.getId())
			.flatMap(project -> buildEvent(project, ProjectEventType.UPDATED, command));
	}

	@Override
	public Mono<TaskEventEntity> deleteOne(DeleteTaskCommand command) {
		return findTask(command.getId())
			.flatMap(project -> buildEvent(project, ProjectEventType.DELETED, command));

	}

	@Override
	public Mono<TaskEventEntity> changeTaskStatus(ChangeStatusCommand command) {
		return sprintClient.findSprintById(command.getSprintId())
			.flatMap(sprint ->
				findTask(command.getTaskId())
					.flatMap(task -> {
						if(task.getStatus().equals(command.getStatusName())) {
							return Mono.error(new TaskAlreadyHasProvidedStatus());
						}
						return buildEvent(task, TaskEventType.CHANGED_TASK_STATUS, command);
					})
			);
	}

	@Override
	public Mono<TaskEventEntity> changeEmployee(ChangeTaskEmployeeCommand command) {
		return findTask(command.getId())
			.flatMap(task -> {
				Employee requiredEmployee = Employee.builder().id(command.getEmployeeId()).build();
				if(task.getEmployee().equals(requiredEmployee)) {
					return Mono.error(new TaskAlreadyAssignedToProvidedEmployee());
				}

				return sprintClient.findSprintById(task.getSprintId())
					.flatMap(sprint -> projectClient.findProjectById(sprint.getProjectId(), true))
					.flatMap(project -> {
						if(!project.getEmployees().contains(requiredEmployee)) {
							return Mono.error(new ProjectDoesNotHaveRequiredPositionException());
						}

						return buildEvent(task, TaskEventType.CHANGED_EMPLOYEE, command);
					});
			});
	}

	@Override
	public Flux<TaskEventEntity> getEvents(GetEventsDto dto) {
		return taskEventsRepository.findManyEvents(dto);
	}

	private Mono<Task> findTask(String id) {
		return taskClient.findTaskById(id)
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.TASK_NOT_FOUND_BY_ID)));
	}

	private Mono<TaskEventEntity> buildEvent(Task task, String eventType, Object command) {
		return taskEventsRepository.findFirstByTaskIdOrderByRevisionDesc(task.getId())
			.flatMap(taskEventEntity -> {
				TaskEventEntity event = TaskEventEntity.builder()
					.id(null)
					.taskId(task.getId())
					.type(eventType)
					.data(command)
					.revision(taskEventEntity.getRevision() + 1)
					.build();
				return taskEventsRepository.save(event);
			});
	}
}
