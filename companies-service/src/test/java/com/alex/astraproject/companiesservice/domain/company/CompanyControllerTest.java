package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class CompanyControllerTest {
    UUID companyId = UUID.randomUUID();
    List<CompanyEventEntity> list;

    @Autowired
    WebTestClient client;

    @Autowired
    CompanyEventsRepository companyEventsRepository;

    @MockBean
    CompanyQueryClient mockCompanyQueryClient;

    @Before
    public void initData() {
        this.list = Arrays.asList(
          new CompanyEventEntity(null, companyId, CompanyEventType.CREATED, null, 1),
          new CompanyEventEntity(null, companyId, CompanyEventType.UPDATED, null, 2),
          new CompanyEventEntity(null, companyId, CompanyEventType.DELETED, null, 3)
        );

        companyEventsRepository.deleteAll()
          .thenMany(Flux.fromIterable(list))
          .flatMap(companyEventsRepository::save)
          .doOnNext(item -> {
              System.out.println("Inserted: " + item);
          })
          .blockLast();
    }
    @Test
    public void createCompanyCommand() {
      CreateCompanyCommand command = new CreateCompanyCommand("some-company", "some-company@gmail.com", "123456");
      client.post().uri("/companies")
        .body(Mono.just(command), CreateCompanyCommand.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody();
    }

    @Test
    public void updateEmployeeCommand() {
        Company mockCompany = new Company();
        mockCompany.setId(companyId);
        Mockito.when(mockCompanyQueryClient.findOneById(anyString())).thenReturn(mockCompany);
        UpdateEmployeeCommand command = new UpdateEmployeeCommand();
        command.setFirstName("Alex");
        command.setLastName("Markus");

        client.patch().uri("/employees/{id}", companyId.toString())
          .body(Mono.just(command), UpdateEmployeeCommand.class)
          .exchange()
          .expectStatus().isAccepted()
          .expectBody()
          .consumeWith(entityExchangeResult -> {
              StepVerifier.create(companyEventsRepository.findAllByCompanyIdAndRevisionGreaterThan(companyId, 0))
                .expectSubscription()
                .expectNextCount(4l)
                .verifyComplete();
          });
    }

    @Test
    public void getListOfEvents() {
        client.get().uri("/companies/{id}/events?revisionFrom={revisionFrom}", companyId, 1)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus().isOk()
          .expectBodyList(EmployeeEventEntity.class)
          .consumeWith(response -> {
              List<EmployeeEventEntity> body = response.getResponseBody();
              assertEquals(2, body.size());
          });
    }

    @Test
    public void deleteEmployeeCommand() {
        client.delete().uri("/companies/{id}", companyId.toString())
          .exchange()
          .expectStatus().isAccepted()
          .expectBody()
          .consumeWith(entityExchangeResult -> {
              StepVerifier.create(companyEventsRepository.findAllByCompanyIdAndRevisionGreaterThan(companyId, 0))
                .expectSubscription()
                .expectNextCount(4l)
                .verifyComplete();
          });
    }
}
