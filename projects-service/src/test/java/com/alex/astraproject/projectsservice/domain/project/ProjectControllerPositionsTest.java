package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.clients.CompanyClient;
import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.ChangeEmployeePositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.RemoveEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.AddPositionCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.positions.RemovePositionCommand;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.entities.Position;
import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.statuses.EmployeeStatus;
import org.apache.commons.compress.utils.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class ProjectControllerPositionsTest {

	String projectId = UUID.randomUUID().toString();
	List<ProjectEventEntity> list;

	@Autowired
	WebTestClient client;

	@Autowired
	ProjectEventsRepository projectEventsRepository;

	@MockBean
	ProjectClient mockProjectQueryClient;

	@MockBean
	EmployeeClient mockEmployeeQueryClient;

	@MockBean
	CompanyClient companyClient;

	@Before
	public void initData() {
		this.list = Arrays.asList(
			ProjectEventEntity.builder()
				.id(null)
				.projectId(projectId)
				.type(ProjectEventType.CREATED)
				.data(null)
				.revision(1)
				.timestamp(new Date().getTime())
				.build(),
			ProjectEventEntity.builder()
				.id(null)
				.projectId(projectId)
				.type(ProjectEventType.UPDATED)
				.data(null)
				.revision(2)
				.timestamp(new Date().getTime())
				.build(),
			ProjectEventEntity.builder()
				.id(null)
				.projectId(projectId)
				.type(ProjectEventType.DELETED)
				.data(null)
				.revision(3)
				.timestamp(new Date().getTime())
				.build()
		);

		projectEventsRepository.deleteAll()
			.thenMany(Flux.fromIterable(list))
			.flatMap(projectEventsRepository::save)
			.doOnNext(item -> {
				System.out.println("Inserted: " + item);
			})
			.blockLast();
	}

	@Test
	public void addPositionToProject() {
		String mockPositionId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setId(projectId);
		mockProject.setCompanyId(UUID.randomUUID().toString());
		mockProject.setPositions(Sets.newHashSet());

		Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));

		AddPositionCommand command = new AddPositionCommand();
		command.setPositionId(mockPositionId);

		client.patch().uri("/projects/{id}/add-position", projectId)
			.body(Mono.just(command), AddPositionCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(projectEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void addPositionToProject_notFoundProject() {
		String mockPositionId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setId(projectId);
		mockProject.setCompanyId(UUID.randomUUID().toString());
		mockProject.setPositions(Sets.newHashSet());

		Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.empty());

		AddPositionCommand command = new AddPositionCommand();
		command.setPositionId(mockPositionId);

		client.patch().uri("/projects/{id}/add-position", projectId)
			.body(Mono.just(command), AddPositionCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
	}

	@Test
	public void addPositionToProject_alreadyHavePosition() {
		String mockPositionId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setId(projectId);
		mockProject.setCompanyId(UUID.randomUUID().toString());
		mockProject.setPositions(Sets.newHashSet(Position.builder().id(mockPositionId).build()));

		Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));

		AddPositionCommand command = new AddPositionCommand();
		command.setPositionId(mockPositionId);

		client.patch().uri("/projects/{id}/add-position", projectId)
			.body(Mono.just(command), AddPositionCommand.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.PROJECT_ALREADY_HAVE_POSITION);
	}

	@Test
	public void removePositionFromProject() {
		String mockPositionId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setId(projectId);
		mockProject.setCompanyId(UUID.randomUUID().toString());
		mockProject.setPositions(Sets.newHashSet(Position.builder().id(mockPositionId).build()));

		Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));

		RemovePositionCommand command = new RemovePositionCommand();
		command.setPositionId(mockPositionId);

		client.patch().uri("/projects/{id}/remove-position", projectId)
			.body(Mono.just(command), RemovePositionCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(projectEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void removePositionFromProject_notFoundProject() {
		String mockPositionId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setId(projectId);
		mockProject.setCompanyId(UUID.randomUUID().toString());
		mockProject.setPositions(Sets.newHashSet(Position.builder().id(mockPositionId).build()));

		Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.empty());

		RemovePositionCommand command = new RemovePositionCommand();
		command.setPositionId(mockPositionId);

		client.patch().uri("/projects/{id}/remove-position", projectId)
			.body(Mono.just(command), RemovePositionCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
	}

	@Test
	public void removePositionFromProject_notFoundPosition() {
		String mockPositionId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setId(projectId);
		mockProject.setCompanyId(UUID.randomUUID().toString());
		mockProject.setPositions(Sets.newHashSet());

		Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));

		RemovePositionCommand command = new RemovePositionCommand();
		command.setPositionId(mockPositionId);

		client.patch().uri("/projects/{id}/remove-position", projectId)
			.body(Mono.just(command), RemovePositionCommand.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.PROJECT_DOES_NOT_HAVE_REQUIRED_POSITION);
	}
}
