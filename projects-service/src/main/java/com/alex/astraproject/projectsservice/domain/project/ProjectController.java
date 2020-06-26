package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.domain.project.commands.common.*;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.ChangeEmployeePositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.AddPositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.RemovePositionCommand;
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
  private ProjectService projectService;

  @Autowired
  private ProjectMessagesService projectMessagesService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createOne(@RequestBody @Valid CreateProjectCommand command) {
    return projectService.createOne(command)
	    .flatMap(projectEventEntity -> {
	      projectMessagesService.sendCreatedEvent(projectEventEntity);
	      return Mono.empty();
	    });
  }

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> deleteOne(@PathVariable String id) {
		return projectService
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
		return projectService
			.updateOne(command)
			.flatMap(event -> {
				projectMessagesService.sendUpdatedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/complete")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> completeOne(@PathVariable String id, @RequestBody @Valid CompleteProjectCommand command) {
		command.setId(id);
		return projectService
			.completeOne(command)
			.flatMap(event -> {
				projectMessagesService.sendCompletedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/stop")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> stopOne(@PathVariable String id, @RequestBody @Valid StopProjectCommand command) {
		command.setId(id);
		return projectService
			.stopOne(command)
			.flatMap(event -> {
				projectMessagesService.sendStoppedEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/add-employee")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> addEmployee(@PathVariable String id, @RequestBody @Valid AddEmployeeCommand command) {
  	command.setProjectId(id);
		System.out.println(command);
  	return projectService.addEmployee(command)
		  .flatMap(event -> {
		  	projectMessagesService.sendAddedParticipantEvent(event);
		    return Mono.empty();
		  });
	}

	@PatchMapping("{id}/remove-employee")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> removeEmployee(@PathVariable String id, @RequestBody @Valid RemoveEmployeeCommand command) {
		command.setProjectId(id);
		return projectService.removeEmployee(command)
			.flatMap(event -> {
				projectMessagesService.sendRemovedParticipantEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/change-employee-position")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> changeEmployeePosition(@PathVariable String id, @RequestBody @Valid ChangeEmployeePositionCommand command) {
		command.setProjectId(id);
		return projectService.changeEmployeePosition(command)
			.flatMap(event -> {
				projectMessagesService.sendChangeEmployeePositionEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/add-position")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> addPositionToProject(@PathVariable String id, @RequestBody @Valid AddPositionCommand command) {
		command.setProjectId(id);
		System.out.println(command);
		return projectService.addPosition(command)
			.flatMap(event -> {
				projectMessagesService.sendAddedPositionEvent(event);
				return Mono.empty();
			});
	}

	@PatchMapping("{id}/remove-position")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<Void> removePositionFromProject(@PathVariable String id, @RequestBody @Valid RemovePositionCommand command) {
		command.setProjectId(id);
		return projectService.removePosition(command)
			.flatMap(event -> {
				projectMessagesService.sendRemovedPositionEvent(event);
				return Mono.empty();
			});
	}

	@GetMapping("{id}/events")
	public Flux<ProjectEventEntity> getEvents(
		@PathVariable @NotBlank String id,
		GetEventsDto dto
	) {
		dto.setEntityId(id);
		return projectService.getEvents(dto);
	}

}
