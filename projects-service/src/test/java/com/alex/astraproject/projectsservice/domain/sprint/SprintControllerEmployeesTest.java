package com.alex.astraproject.projectsservice.domain.sprint;

import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.clients.SprintClient;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.CompleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.CreateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.DeleteSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.common.UpdateSprintCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.statuses.CreateTaskStatusCommand;
import com.alex.astraproject.projectsservice.domain.sprint.commands.statuses.DeleteTaskStatusCommand;
import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.entities.Sprint;
import com.alex.astraproject.shared.entities.Status;
import com.alex.astraproject.shared.eventTypes.SprintEventType;
import com.alex.astraproject.shared.messages.Errors;
import org.apache.commons.compress.utils.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class SprintControllerEmployeesTest {

	String sprintId = UUID.randomUUID().toString();
	List<SprintEventEntity> list;

	@Autowired
	WebTestClient client;

	@Autowired
	SprintEventsRepository sprintEventsRepository;

	@MockBean
	ProjectClient mockProjectClient;

	@MockBean
	SprintClient mockSprintQueryClient;

	@MockBean
	EmployeeClient mockEmployeeQueryClient;

	@Before
	public void initData() {
		this.list = Arrays.asList(
			SprintEventEntity.builder()
				.id(null)
				.sprintId(sprintId)
				.type(SprintEventType.CREATED)
				.data(null)
				.revision(1)
				.timestamp(new Date().getTime())
				.build(),
			SprintEventEntity.builder()
				.id(null)
				.sprintId(sprintId)
				.type(SprintEventType.UPDATED)
				.data(null)
				.revision(2)
				.timestamp(new Date().getTime())
				.build(),
			SprintEventEntity.builder()
				.id(null)
				.sprintId(sprintId)
				.type(SprintEventType.DELETED)
				.data(null)
				.revision(3)
				.timestamp(new Date().getTime())
				.build()
		);

		sprintEventsRepository.deleteAll()
			.thenMany(Flux.fromIterable(list))
			.flatMap(sprintEventsRepository::save)
			.doOnNext(item -> {
				System.out.println("Inserted: " + item);
			})
			.blockLast();
	}

	@Test
	public void createSprintCommand() {
		Project mockProject = new Project();
		mockProject.setId(UUID.randomUUID().toString());
		Mockito.when(mockProjectClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));
		CreateSprintCommand command = new CreateSprintCommand("some-sprint", "some-description", mockProject.getId());
		client.post().uri("/sprints")
			.body(Mono.just(command), CreateSprintCommand.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(sprintEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void createSprintCommand_projectNotFound() {
		Mockito.when(mockProjectClient.findProjectById(anyString())).thenReturn(Mono.empty());
		CreateSprintCommand command = new CreateSprintCommand(
			"some-sprint",
			"some-description",
			UUID.randomUUID().toString()
		);
		client.post().uri("/sprints")
			.body(Mono.just(command), CreateSprintCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
	}

	@Test
	public void updateSprintCommand() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		UpdateSprintCommand command = new UpdateSprintCommand();
		command.setName("Some new sprint name");
		command.setDescription("Some description");

		client.patch().uri("/sprints/{id}", sprintId)
			.body(Mono.just(command), UpdateSprintCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(sprintEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void updateSprintCommand_sprintNotFound() {
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		UpdateSprintCommand command = new UpdateSprintCommand();
		command.setName("Some new sprint name");
		command.setDescription("Some description");

		client.patch().uri("/sprints/{id}", sprintId)
			.body(Mono.just(command), UpdateSprintCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}

	@Test
	public void deleteSprintCommand() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));

		client.delete().uri("/sprints/{id}", sprintId)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(sprintEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void deleteSprintCommand_sprintNotFound() {
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		client.patch().uri("/sprints/{id}", sprintId)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}


	@Test
	public void completeSprintCommand() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		CompleteSprintCommand command = new CompleteSprintCommand();
		command.setId(sprintId);

		client.patch().uri("/sprints/{id}/complete", sprintId)
			.body(Mono.just(command), CompleteSprintCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(sprintEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void completeSprintCommand_notFound() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		CompleteSprintCommand command = new CompleteSprintCommand();
		command.setId(sprintId);

		client.patch().uri("/sprints/{id}/complete", sprintId)
			.body(Mono.just(command), CompleteSprintCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}

	@Test
	public void createTaskStatusToSprint() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		CreateTaskStatusCommand command = new CreateTaskStatusCommand();
		command.setSprintId(sprintId);
		command.setName("Done");

		client.patch().uri("/sprints/{id}/create-task-status", sprintId)
			.body(Mono.just(command), CreateTaskStatusCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(sprintEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void createTaskStatusToSprint_notFound() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		CreateTaskStatusCommand command = new CreateTaskStatusCommand();
		command.setSprintId(sprintId);
		command.setName("Done");

		client.patch().uri("/sprints/{id}/create-task-status", sprintId)
			.body(Mono.just(command), CreateTaskStatusCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}

	@Test
	public void deleteTaskStatusFromSprint() {
		String statusName = "Done";
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		mockSprint.setTaskStatuses(Sets.newHashSet(new Status(statusName)));
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		DeleteTaskStatusCommand command = new DeleteTaskStatusCommand();
		command.setSprintId(sprintId);
		command.setStatusName(statusName);

		client.patch().uri("/sprints/{id}/delete-task-status", sprintId)
			.body(Mono.just(command), DeleteTaskStatusCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(sprintEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void deleteTaskStatusFromSprint_notFound() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(sprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		DeleteTaskStatusCommand command = new DeleteTaskStatusCommand();
		command.setSprintId(sprintId);

		client.patch().uri("/sprints/{id}/delete-task-status", sprintId)
			.body(Mono.just(command), DeleteTaskStatusCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}
}
