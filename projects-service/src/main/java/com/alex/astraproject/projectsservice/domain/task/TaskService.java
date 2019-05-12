package com.alex.astraproject.projectsservice.domain.task;

import com.alex.astraproject.projectsservice.domain.task.commands.ChangeTaskStatusCommand;
import com.alex.astraproject.projectsservice.domain.task.commands.CreateTaskCommand;
import com.alex.astraproject.projectsservice.domain.task.commands.DeleteTaskCommand;
import com.alex.astraproject.projectsservice.domain.task.commands.UpdateTaskCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface TaskService extends EventsService<TaskEventEntity> {

	Mono<TaskEventEntity> createOne(CreateTaskCommand command);

	Mono<TaskEventEntity> updateOne(UpdateTaskCommand command);

	Mono<TaskEventEntity> deleteOne(DeleteTaskCommand command);

	Mono<TaskEventEntity> changeStatus(ChangeTaskStatusCommand command);

	Mono<TaskEventEntity> changeEmployee(Chang)
}
