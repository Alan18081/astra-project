package com.alex.astraproject.projectsservice.domain.sprint;

import com.alex.astraproject.projectsservice.domain.sprint.commands.CompleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.CreateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.DeleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.UpdateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.*;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface SprintService extends EventsService<SprintEventEntity> {

	Mono<SprintEventEntity> createOne(CreateSprintCommand command);

	Mono<SprintEventEntity> updateOne(UpdateSprintCommand command);

	Mono<SprintEventEntity> completeOne(CompleteSprintCommand command);

	Mono<SprintEventEntity> deleteOne(DeleteSprintCommand command);
}
