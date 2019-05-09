package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.domain.project.commands.common.*;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.ChangeEmployeePositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.AddPositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.RemovePositionCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface ProjectService extends EventsService<ProjectEventEntity> {

	Mono<ProjectEventEntity> createOne(CreateProjectCommand command);

	Mono<ProjectEventEntity> updateOne(UpdateProjectCommand command);

	Mono<ProjectEventEntity> deleteOne(DeleteProjectCommand command);

	Mono<ProjectEventEntity> completeOne(CompleteProjectCommand command);

	Mono<ProjectEventEntity> stopOne(StopProjectCommand command);

	Mono<ProjectEventEntity> resumeOne(ResumeProjectCommand command);

	Mono<ProjectEventEntity> addEmployee(AddEmployeeCommand command);

	Mono<ProjectEventEntity> removeEmployee(RemoveEmployeeCommand command);

	Mono<ProjectEventEntity> changeEmployeePosition(ChangeEmployeePositionCommand command);

	Mono<ProjectEventEntity> addPosition(AddPositionCommand command);

	Mono<ProjectEventEntity> removePosition(RemovePositionCommand command);

}
