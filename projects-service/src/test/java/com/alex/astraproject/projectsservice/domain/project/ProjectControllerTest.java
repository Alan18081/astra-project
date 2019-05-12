package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.clients.CompanyClient;
import com.alex.astraproject.projectsservice.clients.EmployeeClient;
import com.alex.astraproject.projectsservice.clients.ProjectClient;
import com.alex.astraproject.projectsservice.domain.project.commands.common.CompleteProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.common.CreateProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.common.StopProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.common.UpdateProjectCommand;
import com.alex.astraproject.projectsservice.domain.project.commands.participants.AddEmployeeCommand;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.entities.Position;
import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.eventTypes.ProjectEventType;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.statuses.EmployeeStatus;
import com.alex.astraproject.shared.statuses.ProjectStatus;
import org.apache.commons.compress.utils.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class ProjectControllerTest {
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
    public void createProjectCommand() {
      Company mockCompany = new Company();
      mockCompany.setId(UUID.randomUUID().toString());
	    Mockito.when(companyClient.findCompanyById(anyString())).thenReturn(Mono.just(mockCompany));
      CreateProjectCommand command = new CreateProjectCommand("some-project", "some-description", mockCompany.getId());
      client.post().uri("/projects")
        .body(Mono.just(command), CreateProjectCommand.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .consumeWith(entityExchangeResult -> {
          StepVerifier.create(projectEventsRepository.findAll())
            .expectSubscription()
            .expectNextCount(4l)
            .verifyComplete();
        });
    }

  @Test
  public void createProjectCommand_companyNotFound() {
    Company mockCompany = new Company();
    mockCompany.setId(UUID.randomUUID().toString());
    Mockito.when(companyClient.findCompanyById(anyString())).thenReturn(Mono.empty());
    CreateProjectCommand command = new CreateProjectCommand("some-project", "some-description", mockCompany.getId());
    client.post().uri("/projects")
      .body(Mono.just(command), CreateProjectCommand.class)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody()
      .jsonPath("$.message").isEqualTo(Errors.COMPANY_NOT_FOUND_BY_ID);
  }

  @Test
  public void updateProjectCommand() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));
    UpdateProjectCommand command = new UpdateProjectCommand();
    command.setName("Some new project name");
    command.setDescription("Some description");

    client.patch().uri("/projects/{id}", projectId)
      .body(Mono.just(command), UpdateProjectCommand.class)
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
  public void completeProjectCommand() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));
    CompleteProjectCommand command = new CompleteProjectCommand();
    command.setId(projectId);

    client.patch().uri("/projects/{id}/complete", projectId)
      .body(Mono.just(command), CompleteProjectCommand.class)
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
  public void completeProjectCommand_notFound() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.empty());
    CompleteProjectCommand command = new CompleteProjectCommand();
    command.setId(projectId);

    client.patch().uri("/projects/{id}/complete", projectId)
      .body(Mono.just(command), CompleteProjectCommand.class)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody()
      .jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
  }

  @Test
  public void stoppedProjectCommand() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));
    StopProjectCommand command = new StopProjectCommand();
    command.setId(projectId);

    client.patch().uri("/projects/{id}/stop", projectId)
      .body(Mono.just(command), StopProjectCommand.class)
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
  public void stoppedProjectCommand_notFound() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.empty());
    StopProjectCommand command = new StopProjectCommand();
    command.setId(projectId);

    client.patch().uri("/projects/{id}/stop", projectId)
      .body(Mono.just(command), StopProjectCommand.class)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody()
      .jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
  }

  @Test
  public void stoppedProjectCommand_invalidStatus() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    mockProject.setStatus(ProjectStatus.STOPPED);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));
    StopProjectCommand command = new StopProjectCommand();
    command.setId(projectId);

    client.patch().uri("/projects/{id}/stop", projectId)
      .body(Mono.just(command), StopProjectCommand.class)
      .exchange()
      .expectStatus().isBadRequest()
      .expectBody()
      .jsonPath("$.message").isEqualTo(Errors.PROJECT_ALREADY_STOPPED);
  }

  @Test
  public void updateProjectCommand_notFound() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.empty());
    UpdateProjectCommand command = new UpdateProjectCommand();
    command.setName("Some new project name");
    command.setDescription("Some description");

    client.patch().uri("/projects/{id}", projectId)
      .body(Mono.just(command), UpdateProjectCommand.class)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody()
      .jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
  }

  @Test
  public void getListOfEvents() {
      client.get().uri("/projects/{id}/events?revisionFrom={revisionFrom}", projectId, 1)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ProjectEventEntity.class)
        .consumeWith(response -> {
            List<ProjectEventEntity> body = response.getResponseBody();
            assertEquals(2, body.size());
        });
  }

  @Test
  public void deleteProjectCommand() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.just(mockProject));
    client.delete().uri("/projects/{id}", projectId)
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
  public void deleteProjectCommand_notFound() {
    Project mockProject = new Project();
    mockProject.setId(projectId);
    Mockito.when(mockProjectQueryClient.findProjectById(anyString())).thenReturn(Mono.empty());
    client.delete().uri("/projects/{id}", projectId)
      .exchange()
      .expectStatus().isNotFound()
      .expectBody()
      .jsonPath("$.message").isEqualTo(Errors.PROJECT_NOT_FOUND_BY_ID);
  }

}
