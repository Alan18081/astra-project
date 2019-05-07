package com.alex.astraproject.gateway.auth;

import com.alex.astraproject.gateway.clients.CompanyClient;
import com.alex.astraproject.gateway.services.AuthService;
import com.alex.astraproject.gateway.services.PasswordService;
import com.alex.astraproject.shared.dto.common.LoginDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.HttpErrorResponse;
import com.alex.astraproject.shared.responses.JwtResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class CompanyAuthControllerTest {
	private Company company = new Company(
		UUID.randomUUID().toString(), "Some company", "company@gmail.com", new ArrayList<>(), "dgfdgfg"
	);

	@Autowired
	WebTestClient client;

	@MockBean
	CompanyClient mockCompanyClient;

	@MockBean
	PasswordService passwordService;

	@MockBean
	AuthService<Company> companyAuthService;

	@Test
	public void login_success() {
		Mockito.when(mockCompanyClient.findCompanyByEmail(anyString())).thenReturn(Mono.just(company));
		LoginDto dto = new LoginDto("company@gmail.com", "password");
		JwtResponse responseBody = client.post().uri("/companies-service/companies/login")
			.body(Mono.just(dto), LoginDto.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(new ParameterizedTypeReference<JwtResponse<Company>>() {})
			.returnResult().getResponseBody();

		assertEquals(responseBody.getData(), company);
		assertNotNull(responseBody.getToken());
	}

	@Test
	public void login_noCompanyFound() {
		Mockito.when(mockCompanyClient.findCompanyByEmail(anyString())).thenReturn(Mono.empty());
		LoginDto dto = new LoginDto("company@gmail.com", "password");
		client.post().uri("/companies-service/companies/login")
			.body(Mono.just(dto), LoginDto.class)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.COMPANY_NOT_FOUND_BY_EMAIL)
			.jsonPath("$.statusCode").isEqualTo(HttpStatus.NOT_FOUND.value())
			.jsonPath("$.timestamp").isNotEmpty();

	}

	@Test
	public void login_wrongPassword() {
		Mockito.when(mockCompanyClient.findCompanyByEmail(anyString())).thenReturn(Mono.just(company));
		Mockito.when(passwordService.comparePassword(anyString(), anyString())).thenReturn(false);
		LoginDto dto = new LoginDto("company@gmail.com", "password");
		client.post().uri("/companies-service/companies/login")
			.body(Mono.just(dto), LoginDto.class)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.INVALID_COMPANY_PASSWORD)
			.jsonPath("$.statusCode").isEqualTo(HttpStatus.BAD_REQUEST.value())
			.jsonPath("$.timestamp").isNotEmpty();

	}

	@Test
	public void authProcess() {
//		Mockito.when(companyAuthService.verifyToken(anyString())).thenReturn(Mono.just(company));
		client.get().uri("/companies-service/companies")
			.header(HttpHeaders.AUTHORIZATION, "Bearer fghjjktygfyjuy")
			.exchange()
//			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.message").isEqualTo(Errors.INVALID_COMPANY_PASSWORD);
	}

}
