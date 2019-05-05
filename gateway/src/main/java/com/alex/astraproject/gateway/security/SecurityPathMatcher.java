package com.alex.astraproject.gateway.security;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public final class SecurityPathMatcher implements ServerWebExchangeMatcher {
	private static final PathPatternParser DEFAULT_PATTERN_PARSER = new PathPatternParser();

	private final PathPattern pattern;
	private final HttpMethod method;

	public SecurityPathMatcher(PathPattern pattern) {
		this(pattern, null);
	}

	public SecurityPathMatcher(String pattern) {
		Assert.notNull(pattern, "pattern cannot be null");
		this.pattern = DEFAULT_PATTERN_PARSER.parse(pattern);
		this.method = null;
	}

	public SecurityPathMatcher(PathPattern pattern, HttpMethod method) {
		Assert.notNull(pattern, "pattern cannot be null");
		this.pattern = pattern;
		this.method = method;
	}

	public SecurityPathMatcher(String pattern, HttpMethod method) {
		Assert.notNull(pattern, "pattern cannot be null");
		this.pattern = DEFAULT_PATTERN_PARSER.parse(pattern);
		this.method = method;
	}

	@Override
	public Mono<MatchResult> matches(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		System.out.println(request.getMethod());
		if (this.method != null && !this.method.equals(request.getMethod())) {
			return MatchResult.notMatch();
		}
		PathContainer path = request.getPath().pathWithinApplication();
		boolean match = this.pattern.matches(path);
		if (!match) {
			return MatchResult.notMatch();
		}
		Map<String, String> pathVariables = this.pattern.matchAndExtract(path).getUriVariables();
		Map<String, Object> variables = new HashMap<>(pathVariables);
		return MatchResult.match(variables);
	}

	@Override
	public String toString() {
		return "PathMatcherServerWebExchangeMatcher{" +
			"pattern='" + pattern + '\'' +
			", method=" + method +
			'}';
	}
}

