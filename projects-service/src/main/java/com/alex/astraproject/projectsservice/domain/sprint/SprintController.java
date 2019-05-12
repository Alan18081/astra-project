package com.alex.astraproject.projectsservice.domain.sprint;

import com.alex.astraproject.projectsservice.domain.sprint.commands.CompleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.CreateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.DeleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.UpdateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.*;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/sprints")
public class SprintController {

  @Autowired
  private SprintService sprintService;

  @Autowired
  private SprintMessagesService sprintMessagesService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createOne(@RequestBody @Valid CreateSprintCommand command) {
    return sprintService.createOne(command)
	    .flatMap(projectEventEntity -> {
		    sprintMessagesService.sendCreatedEvent(projectEventEntity);
	      return Mono.empty();
	    });
  }

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> deleteOne(@PathVariable String id) {
		return sprintService
			.deleteOne(new DeleteSprintCommand(id))
			.flatMap(event -> {
				sprintMessagesService.sendDeletedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> updateOne(@PathVariable String id, @RequestBody @Valid UpdateSprintCommand command) {
		command.setId(id);
		return sprintService
			.updateOne(command)
			.flatMap(event -> {
				sprintMessagesService.sendUpdatedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/complete")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> completeOne(@PathVariable String id, @RequestBody @Valid CompleteSprintCommand command) {
		command.setId(id);
		return sprintService
			.completeOne(command)
			.flatMap(event -> {
				sprintMessagesService.sendCompletedEvent(event);
				return Mono.empty();
			});
	}

	@GetMapping("{id}/events")
	public Flux<SprintEventEntity> getEvents(
		@PathVariable @NotBlank String id,
		GetEventsDto dto
	) {
		dto.setEntityId(id);
		return sprintService.getEvents(dto);
	}

}
