package com.alex.astraproject.projectsservice.domain.project.impl;

import com.alex.astraproject.projectsservice.clients.CompanyClient;
import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventEntity;
import com.alex.astraproject.projectsservice.domain.project.ProjectEventsRepository;
import com.alex.astraproject.projectsservice.domain.project.ProjectService;
import com.alex.astraproject.projectsservice.domain.project.commands.common.*;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.ChangeEmployeePositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.AddPositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.RemovePositionCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.entities.Position;
import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeHasWrongStatusException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveDesiredStatusException;
import com.alex.astraproject.shared.exceptions.projects.ProjectAlreadyHaveRequiredPositionException;
import com.alex.astraproject.shared.exceptions.projects.ProjectDoesNotHaveRequiredEmployeeException;
import com.alex.astraproject.shared.exceptions.projects.ProjectDoesNotHaveRequiredPositionException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.statuses.EmployeeStatus;
import com.alex.astraproject.shared.statuses.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectEventsRepository projectEventsRepository;

	@Autowired
	private ProjectClient projectClient;

	@Autowired
	private EmployeeClient employeeClient;

	@Autowired
	private CompanyClient companyClient;

	@Override
	public Mono<ProjectEventEntity> createOne(CreateProjectCommand command) {
		return companyClient.findCompanyById(command.getCompanyId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)))
			.flatMap(company -> {
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
			});
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

	@Override
	public Mono<ProjectEventEntity> completeOne(CompleteProjectCommand command) {
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
	public Mono<ProjectEventEntity> stopOne(StopProjectCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				if(project.getStatus() == ProjectStatus.STOPPED) {
					return Mono.error(new ProjectAlreadyHaveDesiredStatusException(Errors.PROJECT_ALREADY_STOPPED));
				}
				return buildEvent(project, ProjectEventType.STOPPED, command);
			});
	}

	@Override
	public Mono<ProjectEventEntity> resumeOne(ResumeProjectCommand command) {
		return projectClient.findProjectById(command.getId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				if(project.getStatus() != ProjectStatus.STOPPED) {
					return Mono.error(new ProjectAlreadyHaveDesiredStatusException(Errors.PROJECT_IS_NOT_STOPPED));
				}
				return buildEvent(project, ProjectEventType.RESUMED, command);
			});
	}

	@Override
	public Mono<ProjectEventEntity> addEmployee(AddEmployeeCommand command) {
		return projectClient.findProjectById(command.getProjectId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				Position position = Position.builder().id(command.getPositionId()).build();
				if(!project.getPositions().contains(position)) {
					return Mono.error(new ProjectDoesNotHaveRequiredPositionException());
				}
				return employeeClient.findEmployeeByIdAndCompanyId(command.getEmployeeId(), project.getCompanyId())
					.switchIfEmpty(Mono.error(new NotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID)))
					.flatMap(employee -> {
						if(employee.getStatus() != EmployeeStatus.WORKING) {
							return Mono.error(new EmployeeHasWrongStatusException(Errors.EMPLOYEE_HAS_WRONG_STATUS));
						}
						return buildEvent(project, ProjectEventType.ADDED_EMPLOYEE, command);
					});
			});
	}

	@Override
	public Mono<ProjectEventEntity> removeEmployee(RemoveEmployeeCommand command) {
		return projectClient.findProjectById(command.getProjectId(), true)
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				Employee employeeToRemove = Employee.builder().id(command.getEmployeeId()).build();
				if(!project.getEmployees().contains(employeeToRemove)) {
					return Mono.error(new ProjectDoesNotHaveRequiredEmployeeException());
				};
				return employeeClient.findEmployeeByIdAndCompanyId(command.getEmployeeId(), project.getCompanyId())
					.switchIfEmpty(Mono.error(new NotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID)))
					.flatMap(employee -> buildEvent(project, ProjectEventType.REMOVED_EMPLOYEE, command));
			});
	}

	@Override
	public Mono<ProjectEventEntity> changeEmployeePosition(ChangeEmployeePositionCommand command) {
		return projectClient.findProjectById(command.getProjectId(), true)
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				Employee employeeToRemove = Employee.builder().id(command.getEmployeeId()).build();
				Position position = Position.builder().id(command.getPositionId()).build();
				if(!project.getEmployees().contains(employeeToRemove)) {
					return Mono.error(new ProjectDoesNotHaveRequiredEmployeeException());
				}

				if(!project.getPositions().contains(position)) {
					return Mono.error(new ProjectDoesNotHaveRequiredPositionException());
				}

				return buildEvent(project, ProjectEventType.CHANGED_EMPLOYEE_POSITION, command);
			});
	}

	@Override
	public Mono<ProjectEventEntity> addPosition(AddPositionCommand command) {
		return projectClient.findProjectById(command.getProjectId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				Position positionToAdd = Position.builder().id(command.getPositionId()).build();
				if(project.getPositions().contains(positionToAdd)) {
					return Mono.error(new ProjectAlreadyHaveRequiredPositionException());
				}
				return buildEvent(project, ProjectEventType.ADDED_POSITION, command);
			});
	}

	@Override
	public Mono<ProjectEventEntity> removePosition(RemovePositionCommand command) {
		return projectClient.findProjectById(command.getProjectId())
			.switchIfEmpty(Mono.error(new NotFoundException(Errors.PROJECT_NOT_FOUND_BY_ID)))
			.flatMap(project -> {
				Position positionToAdd = Position.builder().id(command.getPositionId()).build();
				if(!project.getPositions().contains(positionToAdd)) {
					return Mono.error(new ProjectDoesNotHaveRequiredPositionException());
				}
				return buildEvent(project, ProjectEventType.REMOVED_POSITION, command);
			});
	}

	@Override
	public Flux<ProjectEventEntity> getEvents(GetEventsDto dto) {
		return projectEventsRepository.findManyEvents(dto);
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
}
