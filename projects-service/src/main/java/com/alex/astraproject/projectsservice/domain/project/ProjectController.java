package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.domain.project.commands.common.CreateProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.common.DeleteProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.common.UpdateProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddParticipantCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveParticipantCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/projects")
public class ProjectController {

  @Autowired
  private ProjectEventsService projectEventsService;

  @Autowired
  private ProjectMessagesService projectMessagesService;

  @PostMapping
  public Mono<Void> createOne(@RequestBody @Valid CreateProjectCommand command) {
    return projectEventsService.createOne(command)
	    .flatMap(projectEventEntity -> {
	      projectMessagesService.sendCreatedEvent(projectEventEntity);
	      return Mono.empty();
	    });
  }

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> deleteOne(@PathVariable String id) {
		return projectEventsService
			.deleteOne(new DeleteProjectCommand(id))
			.flatMap(event -> {
				projectMessagesService.sendDeletedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> updateOne(@PathVariable String id, @RequestBody @Valid UpdateProjectCommand command) {
		command.setId(id);
		return projectEventsService
			.updateOne(command)
			.flatMap(event -> {
				projectMessagesService.sendUpdatedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/add-participant")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> addParticipant(@PathVariable String id, @RequestBody @Valid AddParticipantCommand command) {
  	command.setProjectId(id);
  	return projectEventsService.addParticipant(command)
		  .flatMap(event -> {
		  	projectMessagesService.sendAddedParticipantEvent(event);
		    return Mono.empty();
		  });
	}

	@PatchMapping("{id}/remove-participant")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> removeParticipant(@PathVariable String id, @RequestBody @Valid RemoveParticipantCommand command) {
		command.setProjectId(id);
		return projectEventsService.removeParticipant(command)
			.flatMap(event -> {
				projectMessagesService.sendRemovedParticipantEvent(event);
				return Mono.empty();
			});
	}

	@GetMapping("{id}/events")
	public Flux<ProjectEventEntity> getEvents(
		@PathVariable @NotBlank String id,
		GetEventsDto dto
	) {
		dto.setEntityId(id);
		return projectEventsService.getEvents(dto);
	}

}
