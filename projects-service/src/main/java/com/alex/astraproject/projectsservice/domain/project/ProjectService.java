package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.domain.project.commands.common.*;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddParticipantCommand;
import reactor.core.publisher.Mono;

public interface ProjectService {

	Mono<ProjectEvent> createOne(CreateProjectCommand command);

	Mono<ProjectEvent> updateOne(UpdateProjectCommand command);

	Mono<ProjectEvent> removeOne(RemoveProjectCommand command);

	Mono<ProjectEvent> completeOne(CompleteProjectCommand command);

	Mono<ProjectEvent> stopOne(StopProjectCommand command);

	Mono<ProjectEvent> resumeOne(ResumeProjectCommand command);

	Mono<ProjectEvent> addParticipant(AddParticipantCommand command);
}
