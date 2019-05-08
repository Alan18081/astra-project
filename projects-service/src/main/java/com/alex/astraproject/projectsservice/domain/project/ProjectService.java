package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.domain.project.commands.common.*;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddParticipantCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveParticipantCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface ProjectEventsService extends EventsService<ProjectEventEntity> {

	Mono<ProjectEventEntity> createOne(CreateProjectCommand command);

	Mono<ProjectEventEntity> updateOne(UpdateProjectCommand command);

	Mono<ProjectEventEntity> deleteOne(DeleteProjectCommand command);

	Mono<ProjectEventEntity> completeOne(CompleteProjectCommand command);

	Mono<ProjectEventEntity> stopOne(StopProjectCommand command);

	Mono<ProjectEventEntity> resumeOne(ResumeProjectCommand command);

	Mono<ProjectEventEntity> addParticipant(AddParticipantCommand command);

	Mono<ProjectEventEntity> removeParticipant(RemoveParticipantCommand command);

}
