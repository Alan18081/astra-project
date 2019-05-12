package com.alex.astraproject.companiesservice.domain.position;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.companiesservice.clients.PositionQueryClient;
import com.alex.astraproject.companiesservice.domain.position.commands.CreatePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.UpdatePositionCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Position;
import com.alex.astraproject.shared.eventTypes.PositionEventType;
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
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class PositionControllerTest {
    String positionId = UUID.randomUUID().toString();
    List<PositionEventEntity> list;

    @Autowired
    WebTestClient client;

    @Autowired
    PositionEventsRepository positionEventsRepository;

    @MockBean
    CompanyQueryClient mockCompanyQueryClient;

    @MockBean
    PositionQueryClient mockPositionQueryClient;

    @Before
    public void initData() {
        this.list = Arrays.asList(
          PositionEventEntity.builder()
            .id(null)
            .positionId(positionId)
            .type(PositionEventType.CREATED)
            .data(null)
            .revision(1)
            .build(),
          PositionEventEntity.builder()
            .id(null)
            .positionId(positionId)
            .type(PositionEventType.UPDATED)
            .data(null)
            .revision(2)
            .build(),
          PositionEventEntity.builder()
            .id(null)
            .positionId(positionId)
            .type(PositionEventType.DELETED)
            .data(null)
            .revision(3)
            .build()
        );

        positionEventsRepository.deleteAll()
          .thenMany(Flux.fromIterable(list))
          .flatMap(positionEventsRepository::save)
          .doOnNext(item -> {
              System.out.println("Inserted: " + item);
          })
          .blockLast();
    }
    @Test
    public void createPositionCommand() {
      Company mockCompany = new Company();
      Mockito.when(mockCompanyQueryClient.findCompanyById(anyString())).thenReturn(Mono.just(mockCompany));
      CreatePositionCommand command = new CreatePositionCommand("some-company", UUID.randomUUID().toString());
      client.post().uri("/positions")
        .body(Mono.just(command), CreatePositionCommand.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody();
    }

    @Test
    public void updatePositionCommand() {
      Position mockPosition = new Position();
      mockPosition.setId(positionId);
      Mockito.when(mockPositionQueryClient.findPositionById(anyString())).thenReturn(Mono.just(mockPosition));
      UpdatePositionCommand command = new UpdatePositionCommand();
      command.setName("Some new position name");

      client.patch().uri("/positions/{id}", positionId)
        .body(Mono.just(command), UpdatePositionCommand.class)
        .exchange()
        .expectStatus().isAccepted()
        .expectBody()
        .consumeWith(entityExchangeResult -> {
            StepVerifier.create(positionEventsRepository.findAll())
              .expectSubscription()
              .expectNextCount(4l)
              .verifyComplete();
        });
    }

    @Test
    public void getListOfEvents() {
        client.get().uri("/positions/{id}/events?revisionFrom={revisionFrom}", positionId, 1)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus().isOk()
          .expectBodyList(PositionEventEntity.class)
          .consumeWith(response -> {
              List<PositionEventEntity> body = response.getResponseBody();
              assertEquals(2, body.size());
          });
    }

    @Test
    public void deletePositionCommand() {
	    Position mockPosition = new Position();
      mockPosition.setId(positionId);
	    Mockito.when(mockPositionQueryClient.findPositionById(anyString())).thenReturn(Mono.just(mockPosition));
	    client.delete().uri("/positions/{id}", positionId)
        .exchange()
        .expectStatus().isAccepted()
        .expectBody()
        .consumeWith(entityExchangeResult -> {
          StepVerifier.create(positionEventsRepository.findAll())
            .expectSubscription()
            .expectNextCount(4l)
            .verifyComplete();
        });
    }
}
