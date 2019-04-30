package com.alex.astraproject.gateway.security;

import com.alex.astraproject.gateway.exceptions.JwtTokenExtractException;
import com.alex.astraproject.gateway.services.CompaniesAuthService;
import com.alex.astraproject.gateway.services.JwtService;
import com.alex.astraproject.shared.dto.companies.VerifyCompanyTokenDto;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.HttpErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.FallbackHeadersGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class JwtFilter implements WebFilter {
	
	@Autowired
	private CompaniesAuthService companiesAuthService;

//	@Override
	public GatewayFilter apply(FallbackHeadersGatewayFilterFactory.Config config) {
		System.out.println("Some issue");
		return (exchange, chain) -> {
			System.out.println("Filtering request");
			try {
				String token = this.extractToken(exchange.getRequest());
				return companiesAuthService.verifyToken(new VerifyCompanyTokenDto(token))
					.flatMap(company -> {
						ServerHttpRequest request = exchange.getRequest().mutate().
							header("SOME_HEADER", company.getEmail()).
							build();
						return chain.filter(exchange.mutate().request(request).build());
					});

			} catch (JwtTokenExtractException e) {
				log.error(e.getMessage());
				return onError(exchange, e.getMessage());
			}
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		HttpErrorResponse body = new HttpErrorResponse(err, System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value());
		byte[] bytes;
		try {
			bytes = new ObjectMapper().writeValueAsString(body).getBytes(StandardCharsets.UTF_8);
		} catch (JsonProcessingException e) {
			bytes = new byte[0];
		}
		DataBuffer buffer = response.bufferFactory().wrap(bytes);
		return response.writeWith(Flux.just(buffer)).then(response.setComplete());
	}

	private String extractToken(ServerHttpRequest request) {
		if(!request.getHeaders().containsKey("Authorization")) {
			throw new JwtTokenExtractException(Errors.AUTH_HEADER_NOT_FOUND);
		}

		List<String> headers = request.getHeaders().get("Authorization");
		if (headers.isEmpty()) {
			throw new JwtTokenExtractException(Errors.AUTH_HEADER_CONTENT_NOT_FOUND);
		}
		String credential = headers.get(0).trim();
		String[] components = credential.split("\\s");

		if(components.length != 2) {
			throw new JwtTokenExtractException(Errors.MALFORMAT_AUTH_HEADER);
		}

		if(!components[0].equals("Bearer")) {
			throw new JwtTokenExtractException(Errors.AUTH_HEADER_BEARER_NOT_FOUND);
		}

		return components[1].trim();
	}

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
		return getA;
	}
}
