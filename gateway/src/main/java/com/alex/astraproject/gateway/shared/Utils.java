package com.alex.astraproject.gateway.shared;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

public final class Utils {

	public static String parseAuthToken(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		return authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
	}
}
