package com.alex.astraproject.queryservice.domain.company;

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
public class CompanyControllerTest {
	List<CompanyEntity> list;

	@Autowired
	WebTestClient client;

	@Autowired
	CompanyRepository companyRepository;

	@Before
	public void initData() {
		this.list = Arrays.asList(
			new CompanyEntity.CompanyEntityBuilder()
				.id(UUID.randomUUID().toString()).name("company1").email("company1@gmail.com").build(),
			new CompanyEntity.CompanyEntityBuilder()
				.id(UUID.randomUUID().toString()).name("company2").email("company2@gmail.com").build(),
			new CompanyEntity.CompanyEntityBuilder()
				.id(UUID.randomUUID().toString()).name("company3").email("company3@gmail.com").build()
			);

		companyRepository.deleteAll();
		companyRepository.saveAll(list);
	}

	@Test
	public void findMany() {
		client.get().uri("/companies")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(CompanyEntity.class)
			.hasSize(3);
	}

	@Test
	public void findOneById() {
		CompanyEntity company = list.get(0);
		client.get().uri("/companies/{id}", company.getId())
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(company.getId())
			.jsonPath("$.name").isEqualTo(company.getName())
			.jsonPath("$.email").isEqualTo(company.getEmail());
	}

	@Test
	public void findOneById_notFound() {
		client.get().uri("/companies/gfdgfdgf")
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	public void findOneByEmail() {
		CompanyEntity company = list.get(0);
		client.get().uri("/companies/email/{email}", company.getEmail())
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(company.getId())
			.jsonPath("$.name").isEqualTo(company.getName())
			.jsonPath("$.email").isEqualTo(company.getEmail());
	}

	@Test
	public void findOneByEmail_notFound() {
		client.get().uri("/companies/email/gfdgfdgf")
			.exchange()
			.expectStatus().isNotFound();
	}
}
