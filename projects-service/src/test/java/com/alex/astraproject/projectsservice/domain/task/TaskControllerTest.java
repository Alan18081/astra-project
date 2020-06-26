package com.alex.astraproject.projectsservice.domain.task;

import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.clients.SprintClient;
import com.alex.astraproject.projectsservice.clients.TaskClient;
import com.alex.astraproject.projectsservice.domain.task.commands.ChangeTaskEmployeeCommand;
import com.alex.astraproject.projectsservice.domain.task.commands.ChangeStatusCommand;
import com.alex.astraproject.projectsservice.domain.task.commands.CreateTaskCommand;
import com.alex.astraproject.projectsservice.domain.task.commands.UpdateTaskCommand;
import com.alex.astraproject.shared.entities.*;
import com.alex.astraproject.shared.eventTypes.TaskEventType;
import com.alex.astraproject.shared.messages.Errors;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class TaskControllerTest {

	String taskId = UUID.randomUUID().toString();
	List<TaskEventEntity> list;

	@Autowired
	WebTestClient client;

	@Autowired
	TaskEventsRepository taskEventsRepository;

	@MockBean
	SprintClient mockSprintQueryClient;

	@MockBean
	TaskClient mockTaskQueryClient;

	@MockBean
	EmployeeClient mockEmployeeQueryClient;

	@MockBean
	ProjectClient mockProjectQueryClient;

	@Before
	public void initData() {
		this.list = Arrays.asList(
			TaskEventEntity.builder()
				.id(null)
				.taskId(taskId)
				.type(TaskEventType.CREATED)
				.data(null)
				.revision(1)
				.build(),
			TaskEventEntity.builder()
				.id(null)
				.taskId(taskId)
				.type(TaskEventType.UPDATED)
				.data(null)
				.revision(2)
				.build(),
			TaskEventEntity.builder()
				.id(null)
				.taskId(taskId)
				.type(TaskEventType.DELETED)
				.data(null)
				.revision(3)
				.build()
		);

		taskEventsRepository.deleteAll()
			.thenMany(Flux.fromIterable(list))
			.flatMap(taskEventsRepository::save)
			.doOnNext(item -> {
				System.out.println("Inserted: " + item);
			})
			.blockLast();
	}

	@Test
	public void createTaskCommand() {
		Sprint mockSprint = new Sprint();
		mockSprint.setId(UUID.randomUUID().toString());
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		CreateTaskCommand command = new CreateTaskCommand("some-task", "some-description", mockSprint.getId());
		client.post().uri("/tasks")
			.body(Mono.just(command), CreateTaskCommand.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(taskEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void createTaskCommand_projectNotFound() {
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		CreateTaskCommand command = new CreateTaskCommand(
			"some-task",
			"some-description",
			UUID.randomUUID().toString()
		);
		client.post().uri("/tasks")
			.body(Mono.just(command), CreateTaskCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}

	@Test
	public void updateTaskCommand() {
		Task mockTask = new Task();
		mockTask.setId(taskId);
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));
		UpdateTaskCommand command = new UpdateTaskCommand();
		command.setName("Some new task name");
		command.setDescription("Some description");

		client.patch().uri("/tasks/{id}", taskId)
			.body(Mono.just(command), UpdateTaskCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(taskEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void updateTaskCommand_taskNotFound() {
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.empty());
		UpdateTaskCommand command = new UpdateTaskCommand();
		command.setName("Some new task name");
		command.setDescription("Some description");

		client.patch().uri("/tasks/{id}", taskId)
			.body(Mono.just(command), UpdateTaskCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.TASK_NOT_FOUND_BY_ID);
	}

	@Test
	public void deleteTaskCommand() {
		Task mockTask = new Task();
		mockTask.setId(taskId);
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));

		client.delete().uri("/tasks/{id}", taskId)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(taskEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void deleteTaskCommand_taskNotFound() {
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.empty());
		client.delete().uri("/tasks/{id}", taskId)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.TASK_NOT_FOUND_BY_ID);
	}


	@Test
	public void changeTaskEmployee() {
		String employeeId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setEmployees(Sets.newHashSet(Employee.builder().id(employeeId).build()));
		Task mockTask = new Task();
		mockTask.setId(taskId);
		mockTask.setSprintId(UUID.randomUUID().toString());
		mockTask.setEmployee(Employee.builder().id(UUID.randomUUID().toString()).build());
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));

		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(
			Sprint.builder().projectId(UUID.randomUUID().toString()).build()
		));
		Mockito.when(mockProjectQueryClient.findProjectById(anyString(), eq(true))).thenReturn(Mono.just(mockProject));
		ChangeTaskEmployeeCommand command = new ChangeTaskEmployeeCommand();
		command.setId(taskId);
		command.setEmployeeId(employeeId);

		client.patch().uri("/tasks/{id}/change-employee", taskId)
			.body(Mono.just(command), ChangeTaskEmployeeCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(taskEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void changeTaskEmployee_taskNotFound() {
		String employeeId = UUID.randomUUID().toString();
		Task mockTask = new Task();
		mockTask.setId(taskId);
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.empty());
		ChangeTaskEmployeeCommand command = new ChangeTaskEmployeeCommand();
		command.setId(taskId);
		command.setEmployeeId(employeeId);

		client.patch().uri("/tasks/{id}/change-employee", taskId)
			.body(Mono.just(command), ChangeTaskEmployeeCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.TASK_NOT_FOUND_BY_ID);
	}

	@Test
	public void changeTaskEmployee_taskAlreadyAssignedToProvidedEmployee() {
		String employeeId = UUID.randomUUID().toString();
		Task mockTask = new Task();
		mockTask.setId(taskId);
		mockTask.setEmployee(Employee.builder().id(employeeId).build());
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));
		ChangeTaskEmployeeCommand command = new ChangeTaskEmployeeCommand();
		command.setId(taskId);
		command.setEmployeeId(employeeId);

		client.patch().uri("/tasks/{id}/change-employee", taskId)
			.body(Mono.just(command), ChangeTaskEmployeeCommand.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.TASK_ALREADY_ASSIGNED_TO_PROVIDED_EMPLOYEE);
	}



	@Test
	public void changeTaskEmployee_employeeNotPartOfProject() {
		String employeeId = UUID.randomUUID().toString();
		Project mockProject = new Project();
		mockProject.setEmployees(Sets.newHashSet());
		Task mockTask = Task.builder()
			.employee(Employee.builder().id(UUID.randomUUID().toString()).build())
			.sprintId(UUID.randomUUID().toString())
			.build();

		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(
			Sprint.builder().projectId(UUID.randomUUID().toString()).build()
		));
		Mockito.when(mockProjectQueryClient.findProjectById(anyString(), eq(true))).thenReturn(Mono.just(mockProject));
		ChangeTaskEmployeeCommand command = new ChangeTaskEmployeeCommand();
		command.setId(taskId);
		command.setEmployeeId(employeeId);

		client.patch().uri("/tasks/{id}/change-employee", taskId)
			.body(Mono.just(command), ChangeTaskEmployeeCommand.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.PROJECT_DOES_NOT_HAVE_REQUIRED_EMPLOYEE);
	}

	@Test
	public void changeTaskStatus() {
		String mockSprintId = UUID.randomUUID().toString();
		Sprint mockSprint = new Sprint();
		mockSprint.setId(mockSprintId);
		Task mockTask = new Task();
		mockTask.setId(taskId);
		mockTask.setSprintId(mockSprintId);
		mockTask.setStatus("Testing");
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));
		ChangeStatusCommand command = new ChangeStatusCommand();
		command.setTaskId(taskId);
		command.setStatusName("Done");
		command.setSprintId(UUID.randomUUID().toString());

		client.patch().uri("/tasks/{id}/change-status", taskId)
			.body(Mono.just(command), ChangeStatusCommand.class)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(taskEventsRepository.findAll())
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}

	@Test
	public void changeTaskStatus_sprintNotFound() {
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.empty());
		ChangeStatusCommand command = new ChangeStatusCommand();
		command.setSprintId(taskId);
		command.setStatusName("Done");

		client.patch().uri("/tasks/{id}/change-status", taskId)
			.body(Mono.just(command), ChangeStatusCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.SPRINT_NOT_FOUND_BY_ID);
	}

	@Test
	public void changeTaskStatus_taskNotFound() {
		String mockSprintId = UUID.randomUUID().toString();
		Sprint mockSprint = new Sprint();
		mockSprint.setId(mockSprintId);
		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.empty());
		ChangeStatusCommand command = new ChangeStatusCommand();
		command.setSprintId(mockSprintId);
		command.setStatusName("Done");

		client.patch().uri("/tasks/{id}/change-status", taskId)
			.body(Mono.just(command), ChangeStatusCommand.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.TASK_NOT_FOUND_BY_ID);
	}

	@Test
	public void changeTaskStatus_taskAlreadyHashProvidedStatus() {
		String mockSprintId = UUID.randomUUID().toString();
		Sprint mockSprint = Sprint.builder().id(mockSprintId).build();
		Task mockTask = Task.builder().id(taskId).sprintId(mockSprintId).status("Testing").build();

		Mockito.when(mockSprintQueryClient.findSprintById(anyString())).thenReturn(Mono.just(mockSprint));
		Mockito.when(mockTaskQueryClient.findTaskById(anyString())).thenReturn(Mono.just(mockTask));
		ChangeStatusCommand command = new ChangeStatusCommand();
		command.setSprintId(mockSprintId);
		command.setStatusName("Testing");

		client.patch().uri("/tasks/{id}/change-status", taskId)
			.body(Mono.just(command), ChangeStatusCommand.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.TASK_ALREADY_HAS_PROVIDED_STATUS);
	}
}
