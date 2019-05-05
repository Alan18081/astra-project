package com.alex.astraproject.gateway.auth;

import com.alex.astraproject.gateway.clients.EmployeeClient;
import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.JwtResponse;
import com.alex.astraproject.shared.services.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class EmployeeAuthControllerTest {
	private Employee employee = new Employee();

	@Autowired
	WebTestClient client;

	@MockBean
	EmployeeClient mockEmployeeClient;

	@Autowired
	PasswordService passwordService;

	@Test
	public void login_success() {
		Mockito.when(mockEmployeeClient.findEmployeeByEmail(anyString())).thenReturn(Mono.just(employee));
		employee.setPassword(passwordService.encryptPassword("password"));
//		Mockito.when(passwordService.comparePassword(anyString(), anyString())).thenReturn(true);
		LoginDto dto = new LoginDto("employee@gmail.com", "password");
		JwtResponse responseBody = client.post().uri("/companies-service/employees/login")
			.body(Mono.just(dto), LoginDto.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(new ParameterizedTypeReference<JwtResponse<Employee>>() {})
			.returnResult().getResponseBody();

		assertEquals(responseBody.getData(), employee);
		assertNotNull(responseBody.getToken());
	}

	@Test
	public void login_noEmployeeFound() {
		Mockito.when(mockEmployeeClient.findEmployeeByEmail(anyString())).thenReturn(Mono.empty());
		employee.setPassword(passwordService.encryptPassword("password"));
		LoginDto dto = new LoginDto("employee@gmail.com", "password");
		client.post().uri("/companies-service/employees/login")
			.body(Mono.just(dto), LoginDto.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.EMPLOYEE_NOT_FOUND_BY_EMAIL)
			.jsonPath("$.statusCode").isEqualTo(HttpStatus.NOT_FOUND.value())
			.jsonPath("$.timestamp").isNotEmpty();

	}

	@Test
	public void login_wrongPassword() {
		Mockito.when(mockEmployeeClient.findEmployeeByEmail(anyString())).thenReturn(Mono.just(employee));
		LoginDto dto = new LoginDto("employee@gmail.com", "password");
		client.post().uri("/companies-service/employees/login")
			.body(Mono.just(dto), LoginDto.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.INVALID_EMPLOYEE_PASSWORD)
			.jsonPath("$.statusCode").isEqualTo(HttpStatus.BAD_REQUEST.value())
			.jsonPath("$.timestamp").isNotEmpty();

	}
//
//	@Test
//	public void authProcess() {
//		Mockito.when(mockEmployeeClient.findEmployeeByEmail(anyString())).thenReturn(Mono.just(employee));
//		Mockito.when(passwordService.comparePassword(anyString(), anyString())).thenReturn(false);
//		client.get().uri("/companies-service/employees/login")
//			.body(Mono.just(dto), LoginDto.class)
//			.exchange()
//			.expectStatus().isBadRequest()
//			.expectBody()
//			.jsonPath("$.message").isEqualTo(Errors.INVALID_COMPANY_PASSWORD)
//			.jsonPath("$.statusCode").isEqualTo(HttpStatus.BAD_REQUEST.value())
//			.jsonPath("$.timestamp").isNotEmpty();
//
//	}

}
