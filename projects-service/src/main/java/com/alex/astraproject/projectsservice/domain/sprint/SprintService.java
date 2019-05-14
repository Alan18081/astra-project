package com.alex.astraproject.projectsservice.domain.sprint;

import com.alex.astraproject.projectsservice.domain.sprint.commands.common.CompleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.CreateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.DeleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.UpdateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.statuses.CreateTaskStatusCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.statuses.DeleteTaskStatusCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface SprintService extends EventsService<SprintEventEntity> {

	Mono<SprintEventEntity> createOne(CreateSprintCommand command);

	Mono<SprintEventEntity> updateOne(UpdateSprintCommand command);

	Mono<SprintEventEntity> completeOne(CompleteSprintCommand command);

	Mono<SprintEventEntity> deleteOne(DeleteSprintCommand command);

	Mono<SprintEventEntity> createTaskStatus(CreateTaskStatusCommand command);

	Mono<SprintEventEntity> deleteTaskStatus(DeleteTaskStatusCommand command);

}
