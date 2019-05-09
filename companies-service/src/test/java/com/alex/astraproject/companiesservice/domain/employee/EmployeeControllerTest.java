package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.clients.EmployeeClient;
import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
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
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class EmployeeControllerTest {
    String employeeId = UUID.randomUUID().toString();
    List<EmployeeEventEntity> list;

    @Autowired
    WebTestClient client;

    @Autowired
    EmployeeEventsRepository employeeEventsRepository;

    @MockBean
    EmployeeClient mockEmployeeQueryClient;

    @MockBean
    CompanyClient companyQueryClient;

    @Before
    public void initData() {
      this.list = Arrays.asList(
        EmployeeEventEntity.builder()
	        .id(null)
	        .employeeId(employeeId)
	        .type(EmployeeEventType.CREATED)
	        .data(null)
	        .revision(1).build(),
	      EmployeeEventEntity.builder()
		      .id(null)
		      .employeeId(employeeId)
		      .type(EmployeeEventType.UPDATED)
		      .data(null)
		      .revision(2).build(),
	      EmployeeEventEntity.builder()
		      .id(null)
		      .employeeId(employeeId)
		      .type(EmployeeEventType.FIRED)
		      .data(null)
		      .revision(3).build()
	    );

	    employeeEventsRepository.deleteAll()
		    .thenMany(Flux.fromIterable(list))
		    .flatMap(employeeEventsRepository::save)
		    .doOnNext(item -> {
			    System.out.println("Inserted: " + item);
		    })
		    .blockLast();
    }

    @Test
    public void createEmployeeCommand() {
    	String mockCompanyId = UUID.randomUUID().toString();
    	Company mockCompany = new Company();
    	mockCompany.setId(mockCompanyId);

	    Mockito.when(companyQueryClient.findCompanyById(anyString())).thenReturn(Mono.just(mockCompany));
	    Mockito.when(mockEmployeeQueryClient.isEmployeeExists(anyString())).thenReturn(Mono.just(false));

      CreateEmployeeCommand command = new CreateEmployeeCommand("Alan", "Morgan", "morgan@gmail.com", "123456", mockCompanyId);
      client.post().uri("/employees")
        .body(Mono.just(command), CreateEmployeeCommand.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
	      .consumeWith(entityExchangeResult -> {
		      StepVerifier.create(employeeEventsRepository.findAll())
			      .expectSubscription()
			      .expectNextCount(4l)
			      .verifyComplete();
	      });
    }

    @Test
    public void updateEmployeeCommand() {
	    Employee mockEmployee = new Employee();
	    mockEmployee.setId(employeeId);
	    Mockito.when(mockEmployeeQueryClient.findEmployeeById(anyString())).thenReturn(Mono.just(mockEmployee));
      UpdateEmployeeCommand command = new UpdateEmployeeCommand();
      command.setFirstName("Alex");
      command.setLastName("Markus");

      client.patch().uri("/employees/{id}", employeeId)
        .body(Mono.just(command), UpdateEmployeeCommand.class)
        .exchange()
        .expectStatus().isAccepted()
        .expectBody()
        .consumeWith(entityExchangeResult -> {
          StepVerifier.create(employeeEventsRepository.findAllByEmployeeIdAndRevisionGreaterThan(employeeId, 0))
		        .expectSubscription()
		        .expectNextCount(4l)
		        .verifyComplete();
	        });
    }

    @Test
		public void getListOfEvents() {
    	client.get().uri("/employees/{id}/events?revisionFrom={revisionFrom}", employeeId, 1)
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
		Employee mockEmployee = new Employee();
		mockEmployee.setId(employeeId);
		Mockito.when(mockEmployeeQueryClient.findEmployeeById(anyString())).thenReturn(Mono.just(mockEmployee));
		client.delete().uri("/employees/{id}", employeeId)
			.exchange()
			.expectStatus().isAccepted()
			.expectBody()
			.consumeWith(entityExchangeResult -> {
				StepVerifier.create(employeeEventsRepository.findAllByEmployeeIdAndRevisionGreaterThan(employeeId, 0))
					.expectSubscription()
					.expectNextCount(4l)
					.verifyComplete();
			});
	}
}
