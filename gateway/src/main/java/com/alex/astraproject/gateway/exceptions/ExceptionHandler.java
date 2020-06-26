package com.alex.astraproject.gateway.exceptions;

import com.alex.astraproject.shared.responses.HttpErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ExceptionHandler implements ErrorWebExceptionHandler {
	@Override
	public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
		ServerHttpResponse response = serverWebExchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		HttpErrorResponse errorResponse = new HttpErrorResponse(throwable.getMessage(), System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value());
		byte[] bytes = new byte[0];
		try {
			bytes = new ObjectMapper().writeValueAsString(errorResponse).getBytes();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
		return response
			.writeWith(Flux.just(dataBuffer));
	}
}
