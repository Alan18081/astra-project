package com.alex.astraproject.queryservice.domain.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@DirtiesContext
@SpringBootTest
public class ProjectControllerTest {
	List<EmployeeEntity> list;

	@Autowired
	WebTestClient client;

	@Autowired
	EmployeeRepository employeeRepository;

	@Before
	public void initData() {
		this.list = Arrays.asList(
			new EmployeeEntity.EmployeeEntityBuilder()
				.id(UUID.randomUUID().toString()).firstName("Alex").email("alex123@gmail.com").build(),
			new EmployeeEntity.EmployeeEntityBuilder()
				.id(UUID.randomUUID().toString()).firstName("Alan").email("alan123@gmail.com").build(),
			new EmployeeEntity.EmployeeEntityBuilder()
				.id(UUID.randomUUID().toString()).firstName("Markus").email("markus123@gmail.com").build()
			);

		employeeRepository.deleteAll();
		employeeRepository.saveAll(list);
	}

	@Test
	public void findMany() {
		client.get().uri("/employees")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(EmployeeEntity.class)
			.hasSize(3);
	}

	@Test
	public void findOneById() {
		EmployeeEntity employeeEntity = list.get(0);
		client.get().uri("/employees/{id}", employeeEntity.getId())
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(employeeEntity.getId())
			.jsonPath("$.firstName").isEqualTo(employeeEntity.getFirstName())
			.jsonPath("$.email").isEqualTo(employeeEntity.getEmail());
	}

	@Test
	public void findOneById_notFound() {
		client.get().uri("/employees/gfdgfdgf")
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	public void findOneByEmail() {
		EmployeeEntity employeeEntity = list.get(0);
		client.get().uri("/employees/email/{email}", employeeEntity.getEmail())
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(employeeEntity.getId())
			.jsonPath("$.firstName").isEqualTo(employeeEntity.getFirstName())
			.jsonPath("$.email").isEqualTo(employeeEntity.getEmail());
	}

	@Test
	public void findOneByEmail_notFound() {
		client.get().uri("/employees/email/gfdgfdgf")
			.exchange()
			.expectStatus().isNotFound();
	}
}
