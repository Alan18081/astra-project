package com.alex.astraproject.projectsservice.domain.task;

import com.alex.astraproject.projectsservice.domain.task.commands.*;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private TaskMessagesService taskMessagesService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createOne(@RequestBody @Valid CreateTaskCommand command) {
    return taskService.createOne(command)
	    .flatMap(taskEventEntity -> {
		    taskMessagesService.sendCreatedEvent(taskEventEntity);
	      return Mono.empty();
	    });
  }

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> deleteOne(@PathVariable String id) {
		return taskService
			.deleteOne(new DeleteTaskCommand(id))
			.flatMap(event -> {
				taskMessagesService.sendDeletedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> updateOne(@PathVariable String id, @RequestBody @Valid UpdateTaskCommand command) {
		command.setId(id);
		return taskService
			.updateOne(command)
			.flatMap(event -> {
				taskMessagesService.sendUpdatedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/change-employee")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> changeEmployee(@PathVariable String id, @RequestBody @Valid ChangeTaskEmployeeCommand command) {
		command.setId(id);
		return taskService
			.changeEmployee(command)
			.flatMap(event -> {
				taskMessagesService.sendUpdatedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/change-status")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> changeStatus(@PathVariable String id, @RequestBody @Valid ChangeStatusCommand command) {
		command.setTaskId(id);
		return taskService
			.changeTaskStatus(command)
			.flatMap(event -> {
				taskMessagesService.sendUpdatedEvent(event);
				return Mono.empty();
			});
	}

	@GetMapping("{id}/events")
	public Flux<TaskEventEntity> getEvents(
		@PathVariable @NotBlank String id,
		GetEventsDto dto
	) {
		dto.setEntityId(id);
		return taskService.getEvents(dto);
	}

}
