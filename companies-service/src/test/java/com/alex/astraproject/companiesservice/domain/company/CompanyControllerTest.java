package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class CompanyControllerTest {
    String companyId = UUID.randomUUID().toString();
    List<CompanyEventEntity> list;

    @Autowired
    WebTestClient client;

    @Autowired
    CompanyEventsRepository companyEventsRepository;

    @MockBean
    CompanyClient mockCompanyQueryClient;

    @Before
    public void initData() {
        this.list = Arrays.asList(
          CompanyEventEntity.builder()
            .id(null)
            .companyId(companyId)
            .type(CompanyEventType.CREATED)
            .data(null)
            .revision(1)
            .timestamp(new Date().getTime())
            .build(),
          CompanyEventEntity.builder()
            .id(null)
            .companyId(companyId)
            .type(CompanyEventType.UPDATED)
            .data(null)
            .revision(2)
            .timestamp(new Date().getTime())
            .build(),
          CompanyEventEntity.builder()
            .id(null)
            .companyId(companyId)
            .type(CompanyEventType.DELETED)
            .data(null)
            .revision(3)
            .timestamp(new Date().getTime())
            .build()
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
	    Mockito.when(mockCompanyQueryClient.isCompanyExistsByEmail(anyString())).thenReturn(Mono.just(false));
      CreateCompanyCommand command = new CreateCompanyCommand("some-company", "some-company@gmail.com", "123456");
      client.post().uri("/companies")
        .body(Mono.just(command), CreateCompanyCommand.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody();
    }

    @Test
    public void updateCompanyCommand() {
        Company mockCompany = new Company();
        mockCompany.setId(companyId);
        Mockito.when(mockCompanyQueryClient.findCompanyById(anyString())).thenReturn(Mono.just(mockCompany));
        UpdateCompanyCommand command = new UpdateCompanyCommand();
        command.setName("Some new company name");

        client.patch().uri("/companies/{id}", companyId)
          .body(Mono.just(command), UpdateCompanyCommand.class)
          .exchange()
          .expectStatus().isAccepted()
          .expectBody()
          .consumeWith(entityExchangeResult -> {
              StepVerifier.create(companyEventsRepository.findAll())
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
	    Company mockCompany = new Company();
	    mockCompany.setId(companyId);
	    Mockito.when(mockCompanyQueryClient.findCompanyById(anyString())).thenReturn(Mono.just(mockCompany));
	    client.delete().uri("/companies/{id}", companyId)
          .exchange()
          .expectStatus().isAccepted()
          .expectBody()
          .consumeWith(entityExchangeResult -> {
              StepVerifier.create(companyEventsRepository.findAllByCompanyIdAndRevisionGreaterThan(
              	companyId, 0, PageRequest.of(0, 100)))
                .expectSubscription()
                .expectNextCount(4l)
                .verifyComplete();
          });
    }
}
