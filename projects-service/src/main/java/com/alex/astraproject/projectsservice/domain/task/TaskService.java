package com.alex.astraproject.projectsservice.domain.task;

import com.alex.astraproject.projectsservice.domain.task.commands.*;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface TaskService extends EventsService<TaskEventEntity> {

	Mono<TaskEventEntity> createOne(CreateTaskCommand command);

	Mono<TaskEventEntity> updateOne(UpdateTaskCommand command);

	Mono<TaskEventEntity> deleteOne(DeleteTaskCommand command);

	Mono<TaskEventEntity> changeTaskStatus(ChangeStatusCommand command);

	Mono<TaskEventEntity> changeEmployee(ChangeTaskEmployeeCommand command);
}
